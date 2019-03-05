package com.finz.rest.slider;

import com.finz.rest.RestListListener;
import com.finz.rest.slider.entity.Slider;

public interface RestSlider {

    void list(final RestListListener<Slider> listener);

}
