package com.clarigo.propertyfinderagent.Utils;

import android.content.Context;
import android.graphics.Typeface;

public class TypeFactory {
    final String LATO_REGULAR = "fonts/PoppinsBlack.ttf";


    Typeface PoppinsBlack, PoppinsBlackItalic, PoppinsBold, PoppinsBoldItalic, PoppinsExtraBold, PoppinsExtraBoldItalic,
            PoppinsExtraLight, PoppinsExtraLightItalic, PoppinsItalic, PoppinsLight, PoppinsLightItalic,
            PoppinsMedium, PoppinsMediumItalic, PoppinsRegular, PoppinsSemiBold, PoppinsSemiBoldItalic,
            PoppinsThin, PoppinsThinItalic;

    // static variable single_instance of type Singleton
    private static TypeFactory mFontFactory = null;

    // static method to create instance of Singleton class
    public static TypeFactory getInstance(Context context) {
        if (mFontFactory == null) {
            mFontFactory = new TypeFactory(context);
        }
        return mFontFactory;
    }

    public TypeFactory(Context context) {
        PoppinsBlack = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsBlack.ttf");
        PoppinsBlackItalic = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsBlackItalic.ttf");
        PoppinsBold = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsBold.ttf");
        PoppinsBoldItalic = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsBoldItalic.ttf");
        PoppinsExtraBold = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsExtraBold.ttf");
        PoppinsExtraBoldItalic = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsExtraBoldItalic.ttf");
        PoppinsExtraLight = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsExtraLight.ttf");
        PoppinsExtraLightItalic = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsExtraLightItalic.ttf");
        PoppinsItalic = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsItalic.ttf");
        PoppinsLight = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsLight.ttf");
        PoppinsLightItalic = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsLightItalic.ttf");
        PoppinsMedium = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsMedium.ttf");
        PoppinsMediumItalic = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsMediumItalic.ttf");
        PoppinsRegular = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsRegular.ttf");
        PoppinsSemiBold = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsSemiBold.ttf");
        PoppinsSemiBoldItalic = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsSemiBoldItalic.ttf");
        PoppinsThin = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsThin.ttf");
        PoppinsThinItalic = Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsThinItalic.ttf");
    }
    public Typeface PoppinsBlack() { return PoppinsBlack; }
    public Typeface PoppinsBlackItalic() { return PoppinsBlackItalic; }
    public Typeface PoppinsBold() { return PoppinsBold; }
    public Typeface PoppinsBoldItalic() { return PoppinsBoldItalic; }
    public Typeface PoppinsExtraBold() { return PoppinsExtraBold; }
    public Typeface PoppinsExtraBoldItalic() { return PoppinsExtraBoldItalic; }
    public Typeface PoppinsExtraLight() { return PoppinsExtraLight; }
    public Typeface PoppinsExtraLightItalic() { return PoppinsExtraLightItalic; }
    public Typeface PoppinsItalic() { return PoppinsItalic; }
    public Typeface PoppinsLight() { return PoppinsLight; }
    public Typeface PoppinsLightItalic() { return PoppinsLightItalic; }
    public Typeface PoppinsMedium() { return PoppinsMedium; }
    public Typeface PoppinsMediumItalic() { return PoppinsMediumItalic; }
    public Typeface PoppinsRegular() { return PoppinsRegular; }
    public Typeface PoppinsSemiBold() { return PoppinsSemiBold; }
    public Typeface PoppinsSemiBoldItalic() { return PoppinsSemiBoldItalic; }
    public Typeface PoppinsThin() { return PoppinsThin; }
    public Typeface PoppinsThinItalic() { return PoppinsThinItalic; }



}