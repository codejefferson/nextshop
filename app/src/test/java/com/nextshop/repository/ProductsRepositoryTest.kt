package com.nextshop.repository

import org.junit.Test

class ProductsRepositoryTest {

    companion object {
        private const val DEFAULT_PAGE_INIT = 1
        private const val DEFAULT_TAG = "kitten"
        private const val DEFAULT_PHOTOS_PER_PAGE = 2
        private const val FILTER_TILE_LARGE_SQUARE = "Large Square"
        private const val FILTER_TILE_LARGE = "Large"
    }

    @Test
    fun getPhotos_test() {
        // TODO
        //val expected = ArrayList<PhotoData>()
        //val result = runBlocking { PhotoRepository.refreshPhotos(DEFAULT_TAG, DEFAULT_PAGE_INIT, DEFAULT_PHOTOS_PER_PAGE) }
    }

    @Test
    fun getPhoto_test() {
    }
}