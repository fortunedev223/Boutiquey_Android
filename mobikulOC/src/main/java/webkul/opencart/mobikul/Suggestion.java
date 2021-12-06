package webkul.opencart.mobikul;

import android.text.Html;

public class Suggestion {
String id, name;

public Suggestion(String id, String name) {
this.id = id;
this.name = name;
}

public String getId() {
return id;
}

public String getName() {
return String.valueOf(Html.fromHtml(name));
}
}