#include <cstdarg>
#include <cstdint>
#include <cstdlib>
#include <ostream>
#include <new>

extern "C" {

/// # Safety
///
/// you need to pre-allocate buffer for keyword and success
/// You need to free the c string return by this function.
char *search(const char *keyword, const char *index_path, unsigned int limit, int *success);

} // extern "C"
