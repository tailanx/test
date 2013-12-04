LOCAL_PATH := $(call my-dir)

cmd-strip = $(TOOLCHAIN_PREFIX)strip --strip-debug -x $1

include $(CLEAR_VARS)

LOCAL_MODULE    := Mall 
LOCAL_SRC_FILES := Mall.c \
				   md5.c 
LOCAL_CFLAGS := -DANDROID_NDK \
                -DDISABLE_IMPORTGL \
                -fvisibility=hidden
                
LOCAL_LDLIBS := -lGLESv1_CM -ldl -llog
#LOCAL_SHARED_LIBRARIES := entryex

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := libentryex
LOCAL_SRC_FILES := libentryex.so
include $(PREBUILT_SHARED_LIBRARY)
