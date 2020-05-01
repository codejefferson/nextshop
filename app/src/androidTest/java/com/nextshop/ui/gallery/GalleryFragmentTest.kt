package com.nextshop.ui.gallery

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nextshop.R
import com.nextshop.ui.ParentActivity
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class GalleryFragmentTest {

    @get:Rule
    val parentActivityTestRule = ActivityTestRule(ParentActivity::class.java)

    @Before
    fun setup() {
        // TODO - https://github.com/JakeWharton/okhttp-idling-resource/issues/10
    }

    @Test
    fun galleryScreen_showProgressTest() {
        onView(withId(R.id.state_progress_gallery)).check(matches(isDisplayed()))
    }

    @Test
    fun galleryScreen_showGridTest() {
        onView(withId(R.id.gallery_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun galleryScreen_showListTest() {
        waitSleep(5000)

        onView(withId(R.id.gallery_rv)).check(matches(isDisplayed()))

        onView(withId(R.id.state_progress_gallery)).check(matches(not(isDisplayed())))
        onView(withId(R.id.state_without_conn_gallery)).check(matches(not(isDisplayed())))
    }

    @Test
    fun galleryScreen_scrollTest() {
        waitSleep(5000)

        onView(withId(R.id.gallery_rv))
            .perform(RecyclerViewActions.scrollToPosition<GalleryItemViewHolder>(3))

        waitSleep(2000)
        onView(withId(R.id.gallery_rv))
            .perform(ViewActions.swipeUp())
            .perform(RecyclerViewActions.scrollToPosition<GalleryItemViewHolder>(2))

        waitSleep(2000)
        onView(withId(R.id.gallery_rv))
            .perform(RecyclerViewActions.scrollToPosition<GalleryItemViewHolder>(3))

        waitSleep(2000)
        onView(withId(R.id.gallery_rv))
            .perform(RecyclerViewActions.scrollToPosition<GalleryItemViewHolder>(0))

        waitSleep(2000)
        onView(withId(R.id.gallery_rv)).check(matches(isDisplayed()))
    }

    @Test
    fun galleryScreen_clickOnItem_showDetailTest() {
        waitSleep(5000)
        onView(withId(R.id.gallery_rv))
            .perform(RecyclerViewActions.scrollToPosition<GalleryItemViewHolder>(0))
            .perform(click())

        waitSleep(3000)
        onView(withId(R.id.detail_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun galleryScreen_clickOnItem_showDetailBackToGalleryTest() {
        waitSleep(5000)
        onView(withId(R.id.gallery_rv))
            .perform(RecyclerViewActions.scrollToPosition<GalleryItemViewHolder>(0))
            .perform(click())

        waitSleep(3000)
        onView(withId(R.id.detail_fragment)).check(matches(isDisplayed()))

        onView(isRoot()).perform(pressBack())
        waitSleep(3000)
        onView(withId(R.id.gallery_rv)).check(matches(isDisplayed()))
    }

    private fun waitSleep(time: Long) {
        Thread.sleep(time)
    }


}