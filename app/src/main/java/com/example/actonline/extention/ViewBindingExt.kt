package com.example.actonline.extention

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.actonline.base.BaseActivity
import com.example.actonline.base.BaseFragment
import java.lang.reflect.ParameterizedType

/**
 * Created by Dinh Sam Vu on 4/8/2021.
 */

internal fun <V : ViewBinding> Class<*>.getBinding(layoutInflater: LayoutInflater): V {
    return try {
        @Suppress("UNCHECKED_CAST")
        getMethod(
            "inflate",
            LayoutInflater::class.java
        ).invoke(null, layoutInflater) as V
    } catch (ex: Exception) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
    }
}

internal fun <V : ViewBinding> Class<*>.getBinding(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
): V {
    return try {
        @Suppress("UNCHECKED_CAST")
        getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, layoutInflater, container, false) as V
    } catch (ex: Exception) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
    }
}

internal fun Class<*>.checkMethod(): Boolean {
    return try {
        getMethod(
            "inflate",
            LayoutInflater::class.java
        )
        true
    } catch (ex: Exception) {
        false
    }
}

internal fun Any.findClass(): Class<*> {
    var javaClass: Class<*> = this.javaClass
    var result: Class<*>? = null
    while (result == null || !result.checkMethod()) {
        result = (javaClass
            .genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments?.firstOrNull {
                if (it is Class<*>) {
                    it.checkMethod()
                } else {
                    false
                }
            } as? Class<*>
        javaClass = javaClass.superclass
    }
    return result
}

inline fun <reified V : ViewBinding> ViewGroup.toBinding(): V {
    return V::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    ).invoke(null, LayoutInflater.from(context), this, false) as V
}

internal fun <V : ViewBinding> BaseActivity<V>.getBinding(): V {
    return findClass().getBinding(layoutInflater)
}

internal fun <V : ViewBinding> BaseFragment<V>.getBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
): V {
    return findClass().getBinding(inflater, container)
}
//    internal fun <V : ViewBinding> BaseBottomSheetFrag<V>.getBinding(
//        inflater: LayoutInflater,
//        container: ViewGroup?
//    ): V {
//        return findClass().getBinding(inflater, container)
//    }
//
//
//    internal fun <V : ViewBinding> BaseDialogFragment<V>.getBinding(
//        inflater: LayoutInflater,
//        container: ViewGroup?
//    ): V {
//        return findClass().getBinding(inflater, container)
//    }
