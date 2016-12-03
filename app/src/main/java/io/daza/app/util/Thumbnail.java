package io.daza.app.util;

import io.daza.app.BuildConfig;

public class Thumbnail {

    private String imageUrl = "";

    private int defaultWidth = 120;
    private int defaultHeight = 120;

    public Thumbnail(String value) {
        if (value != null) {
            this.imageUrl = value;
        }
    }

    private String imageView2(int size) {
        int width = defaultWidth;
        int height = defaultHeight;
        if (this.imageUrl.contains(BuildConfig.DOMAIN_NAME) ||
                this.imageUrl.contains("clouddn.com") ||
                this.imageUrl.contains("qnssl.com")) {
            if (size > 0) {
                width = size;
                height = size;
            }
            return this.imageUrl + "?imageView2/2/w/" + width + "/h/" + height;
        }
        return this.imageUrl;
    }

    public String small() {
        return this.imageView2(200);
    }

    public String medium() {
        return this.imageView2(800);
    }

    public String large() {
        return this.imageView2(1200);
    }
}
