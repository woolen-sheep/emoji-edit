//
// Created by xzcxzcyy on 21-1-26.
//
#include "jni.h"
#include "rs-search.h"

extern "C" JNIEXPORT jstring JNICALL
Java_top_woolensheep_emojiedit_Search_jniSearch(
        JNIEnv *env, jobject call, jstring j_request, jstring j_path_to_indices) {
//    return env->NewStringUTF("123");
    int success;
    auto is_copy = (jboolean *) malloc(sizeof(jboolean));
    const char *request = env->GetStringUTFChars(j_request, is_copy);
    const char *path_to_indices = env->GetStringUTFChars(j_path_to_indices, is_copy);
    char *result = search(request, path_to_indices, 80, &success);
    auto value = env->NewStringUTF(result);
    free(is_copy);
    free(result);
    env->ReleaseStringUTFChars(j_request,request);
    env->ReleaseStringUTFChars(j_path_to_indices,path_to_indices);
    return value;
}
