package flowableoperations

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.math.pow
import kotlin.system.exitProcess

// https://pamartinezandres.com/rxjava-2-exponential-backoff-retry-only-when-internet-is-available-5a46188ab175

fun <T> Flowable<T>.addRetryWithBackOff(retriesLimit: Int = 3, initialDelay: Double = 2.0): Flowable<T> =
    retryWhen { errors: Flowable<Throwable> ->
        errors.zipWith(
            Flowable.range(1, retriesLimit + 1),
            BiFunction<Throwable, Int, Int> { error: Throwable, retryCount: Int ->
                if (retryCount > retriesLimit) {
                    println(if (error is IOException) "No network error" else error.localizedMessage)
                    exitProcess(0)
                } else {
                    retryCount
                }
            }
        ).flatMap { retryCount: Int ->
            Flowable.timer(initialDelay.pow(retryCount.toDouble()).toLong(), TimeUnit.SECONDS)
        }
    }