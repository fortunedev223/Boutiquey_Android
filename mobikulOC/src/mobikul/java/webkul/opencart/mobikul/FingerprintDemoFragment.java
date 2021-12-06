package webkul.opencart.mobikul;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

public class FingerprintDemoFragment extends BottomSheetDialogFragment implements FingerprintHandler.Callback {

    private static final String KEY_NAME = "iVsciurrdN";
    static int requestCode;
    FingerprintManager fingerprintManager;
    KeyguardManager keyguardManager;
    KeyGenerator keyGenerator;
    FingerprintManager.CryptoObject cryptoObject;
    private KeyStore keyStore;
    private Cipher cipher;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
    private LoginActivity ctx;

    public FingerprintDemoFragment() {
    }

    public static FingerprintDemoFragment newInstance(int tag, int req) {
        FingerprintDemoFragment frag = new FingerprintDemoFragment();
        requestCode = req;
        Bundle argsBundle = new Bundle();
        argsBundle.putInt("tag", tag);
        frag.setArguments(argsBundle);
        return frag;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
         ctx = (LoginActivity) getActivity();
        View contentView;
        if (getArguments() != null && getArguments().getInt("tag") == 1) {
            contentView = View.inflate(getContext(), R.layout.signin_using_fingerprint, null);
        } else
            contentView = View.inflate(getContext(), R.layout.add_fingerprint_login, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Context mcontext = getActivity();
            //initializing keyguard and fingerprint manager
            keyguardManager =
                    (KeyguardManager) mcontext.getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) mcontext.getSystemService(FINGERPRINT_SERVICE);
            //check whether keyguard is enabled or not
            if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(mcontext, "Not Enabled ", Toast.LENGTH_LONG).show();
                return;
            }

            //check whether fingerprint lock is enabled or not
            if (ActivityCompat.checkSelfPermission(mcontext,
                    android.Manifest.permission.USE_FINGERPRINT) !=
                    PackageManager.PERMISSION_GRANTED) {
                return;
            }

            //check whether fingerprint is registered or not
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                // This happens when no fingerprints are registered.
                Toast.makeText(mcontext, "Registered First", Toast.LENGTH_LONG).show();
                return;
            }
            generateKey();
            if (cipherInit()) {
                cryptoObject = new FingerprintManager.CryptoObject(cipher);
                FingerprintHandler helper = new FingerprintHandler(mcontext, this);
                helper.startAuth(fingerprintManager, cryptoObject);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    protected void generateKey() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //keystore instance needed for getting key access
            try {
                keyStore = KeyStore.getInstance("AndroidKeyStore");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // key generater for generating new key
            try {
                keyGenerator = KeyGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_AES,
                        "AndroidKeyStore");
            } catch (NoSuchAlgorithmException |
                    NoSuchProviderException e) {
                throw new RuntimeException("Failed", e);
            }
            // generating key
            try {
                keyStore.load(null);
                keyGenerator.init(new
                        KeyGenParameterSpec.Builder(KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                                KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
                keyGenerator.generateKey();
            } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                    | CertificateException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @TargetApi(23)
    public boolean cipherInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cipher = Cipher.getInstance(
                        KeyProperties.KEY_ALGORITHM_AES + "/"
                                + KeyProperties.BLOCK_MODE_CBC + "/"
                                + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            } catch (NoSuchAlgorithmException |
                    NoSuchPaddingException e) {
                throw new RuntimeException("Failed to get Cipher", e);
            }
            try {
                keyStore.load(null);
                SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                        null);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                return true;
            } catch (KeyPermanentlyInvalidatedException e) {
                return false;
            } catch (KeyStoreException | CertificateException
                    | UnrecoverableKeyException | IOException
                    | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException("Failed to init Cipher", e);
            }
        }
        return false;
    }

    @Override
    public void onAuthenticated() {
        ctx.FingerPrintResult(true, requestCode);
        dismiss();
    }

    @Override
    public void onError() {
        Toast.makeText(ctx, "Authentication Failed", Toast.LENGTH_LONG).show();
        ctx.FingerPrintResult(false, requestCode);
        dismiss();
    }
}