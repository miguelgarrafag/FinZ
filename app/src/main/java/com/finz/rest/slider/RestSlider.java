package com.finz.rest.slider;

import com.finz.rest.RestListener;
import com.finz.rest.slider.entity.Slider;

public interface RestSlider {

    void list(String refreshToken, final RestListener<Slider> listener);

}
