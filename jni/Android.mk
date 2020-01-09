LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := wzc_webrtc_vad

MY_C_LIST := $(wildcard $(LOCAL_PATH)/*.c)
MY_C_LIST += $(wildcard $(LOCAL_PATH)/common_audio/signal_processing/*.c)
MY_C_LIST += $(wildcard $(LOCAL_PATH)/common_audio/vad/*.c)

LOCAL_SRC_FILES := $(MY_C_LIST:$(LOCAL_PATH)/%=%)

include $(BUILD_SHARED_LIBRARY)