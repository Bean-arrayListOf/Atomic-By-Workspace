import org.myrtle.atomic.LibraryKit

object Cout {

	init {
		LibraryKit.loadLibrary("libcout-lib")
	}

	@JvmStatic
	external fun printf(s: String,vararg args: Any?)
}