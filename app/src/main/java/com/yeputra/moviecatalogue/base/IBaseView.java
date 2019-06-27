package com.yeputra.moviecatalogue.base;

import android.content.Context;


/**
 * Created by yovi.putra
 *    on 09/Mar/2019 10:55
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */
public interface IBaseView {

    Context getContextView();

    void onShowProgressbar();

    void onHideProgressbar();

    void onViewModelFailed(String message);

}
