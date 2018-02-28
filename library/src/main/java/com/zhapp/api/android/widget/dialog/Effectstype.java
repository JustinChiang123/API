package com.zhapp.api.android.widget.dialog;


import com.zhapp.api.android.widget.dialog.effects.BaseEffects;
import com.zhapp.api.android.widget.dialog.effects.FadeIn;
import com.zhapp.api.android.widget.dialog.effects.FlipH;
import com.zhapp.api.android.widget.dialog.effects.FlipV;
import com.zhapp.api.android.widget.dialog.effects.NewsPaper;
import com.zhapp.api.android.widget.dialog.effects.SideFall;
import com.zhapp.api.android.widget.dialog.effects.SlideLeft;
import com.zhapp.api.android.widget.dialog.effects.SlideRight;
import com.zhapp.api.android.widget.dialog.effects.SlideTop;

/**
 * Created by lee on 2014/7/30.
 */
public enum Effectstype {

    Fadein(FadeIn.class), Slideleft(SlideLeft.class), Slidetop(SlideTop.class), SlideBottom(SlideBottom.class), Slideright(SlideRight.class), Fall(Fall.class), Newspager(NewsPaper.class), Fliph(FlipH.class), Flipv(FlipV.class), RotateBottom(RotateBottom.class), RotateLeft(RotateLeft.class), Slit(Slit.class), Shake(Shake.class), Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects = null;
        try {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (InstantiationException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e) {
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
}
