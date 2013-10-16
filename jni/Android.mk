LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Mall
LOCAL_SRC_FILES := Mall.c \
				   md5.c

include $(BUILD_SHARED_LIBRARY)
