package com.air.quality.injector.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by hoanghiep on 2/28/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication {
}
