package com.muslimlife.tf.footballappkotlang.generics

open class BasePresenter<V : BaseView> : BaseMvpPresenter<V> {

    /**
     * Attached view
     */
    var view: V? = null


    override val isAttached: Boolean = view != null


    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        view = null
    }
}