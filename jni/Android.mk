LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Mall 
LOCAL_SRC_FILES := Mall.c \
				   md5.c 
#LOCAL_SHARED_LIBRARIES := entryex

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := libentryex
LOCAL_SRC_FILES := libentryex.so
include $(PREBUILT_SHARED_LIBRARY)
