package webkul.opencart.mobikul;

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class DownloadableProductInfo {
    String orderId, fileName, size, dateAdded ,extension, url;

    DownloadableProductInfo(String order_id, String date_added, String proName, String size, String extension, String url) {
        this.orderId = order_id;
        this.fileName = proName;
        this.size = size;
        this.dateAdded = date_added;
        this.extension = extension;
        this.url = url;
    }
}
