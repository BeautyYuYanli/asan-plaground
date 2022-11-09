#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_asanplayground_MainActivity_helloFromJNI(JNIEnv *env, jobject thiz) {
    return 256;
}

// useAfterFree
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_asanplayground_MainActivity_useAfterFree(JNIEnv *env, jobject thiz) {
    int *array = new int[100];
    delete [] array;
    return array[0];
}

// heapBufferOverflow
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_asanplayground_MainActivity_heapBufferOverflow(JNIEnv *env, jobject thiz) {
    int *array = new int[100];
    array[0] = 0;
    int res = array[1 + 100];  // BOOM
    delete [] array;
    return res;
}

// stackBufferOverflow
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_asanplayground_MainActivity_stackBufferOverflow(JNIEnv *env, jobject thiz) {
    int stack_array[100];
    stack_array[1] = 0;
    return stack_array[1 + 100];  // BOOM
}

// globalBufferOverflow
int global_array[100] = {-1};

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_asanplayground_MainActivity_globalBufferOverflow(JNIEnv *env, jobject thiz) {
    return global_array[1 + 100];
}

// stackUseAfterReturn
int *ptr;
void FunctionThatEscapesLocalObject() {
    int local[100];
    ptr = &local[0];
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_asanplayground_MainActivity_stackUseAfterReturn(JNIEnv *env, jobject thiz) {
    FunctionThatEscapesLocalObject();
    return ptr[1];
}

// stackUseAfterScope
volatile int *p = 0;

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_asanplayground_MainActivity_stackUseAfterScope(JNIEnv *env, jobject thiz) {
    {
        int x = 0;
        p = &x;
    }
    *p = 5;
    return 0;
}