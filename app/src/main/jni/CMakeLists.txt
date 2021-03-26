cmake_minimum_required(VERSION 3.4.1)

file(GLOB RSSEARCH
        ${PROJECT_SOURCE_DIR}/../../../libs/arm64-v8a/librs_search.so)

include_directories("${PROJECT_SOURCE_DIR}/include")

add_library(emoji_search SHARED emoji-search.cpp include/rs-search.h)
target_link_libraries(emoji_search android log ${RSSEARCH})