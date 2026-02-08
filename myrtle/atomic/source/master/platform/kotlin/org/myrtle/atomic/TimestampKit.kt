package org.myrtle.atomic

import java.time.Duration

object TimestampKit {
	@Suppress("NOTHING_TO_INLINE")
	inline fun ofDays(days: Long): Duration = Duration.ofDays(days)

	@Suppress("NOTHING_TO_INLINE")
	inline fun ofHours(hours: Long): Duration = Duration.ofHours(hours)

	@Suppress("NOTHING_TO_INLINE")
	inline fun ofMinutes(minutes: Long): Duration = Duration.ofMinutes(minutes)

	@Suppress("NOTHING_TO_INLINE")
	inline fun ofSeconds(seconds: Long): Duration = Duration.ofSeconds(seconds)

	@Suppress("NOTHING_TO_INLINE")
	inline fun ofSeconds(seconds: Long,nanoAdjustment: Long): Duration = Duration.ofSeconds(seconds,nanoAdjustment)

	@Suppress("NOTHING_TO_INLINE")
	inline fun ofMillis(millis: Long): Duration = Duration.ofMillis(millis)

	@Suppress("NOTHING_TO_INLINE")
	inline fun ofNanos(nanos: Long): Duration = Duration.ofNanos(nanos)
}