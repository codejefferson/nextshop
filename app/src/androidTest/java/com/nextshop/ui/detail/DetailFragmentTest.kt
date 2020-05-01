package com.nextshop.ui.detail

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nextshop.R
import com.nextshop.ui.ParentActivity
import com.nextshop.ui.gallery.GalleryItemViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailFragmentTest {

    @get:Rule
    val parentActivityTestRule = ActivityTestRule(ParentActivity::class.java)

    @Test
    fun detailScreen_showDetailTest() {
        waitSleep(5000)
        onView(withId(R.id.gallery_rv))
            .perform(RecyclerViewActions.scrollToPosition<GalleryItemViewHolder>(0))
            .perform(click())

        waitSleep(3000)
        onView(withId(R.id.detail_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun detailScreen_showImageInDetailTest() {
        waitSleep(5000)
        onView(withId(R.id.gallery_rv))
            .perform(RecyclerViewActions.scrollToPosition<GalleryItemViewHolder>(0))
            .perform(click())

        waitSleep(3000)
        onView(withId(R.id.product_viewpager)).check(matches(isDisplayed()))
    }

    @Test
    fun detailScreen_showContentInDetailTest() {
        waitSleep(5000)
        onView(withId(R.id.gallery_rv))
            .perform(RecyclerViewActions.scrollToPosition<GalleryItemViewHolder>(0))
            .perform(click())

        waitSleep(3000)
        onView(withId(R.id.product_container_content)).check(matches(isDisplayed()))
        onView(withId(R.id.product_description)).check(matches(isDisplayed()))
        onView(withId(R.id.product_name)).check(matches(isDisplayed()))
        onView(withId(R.id.product_price)).check(matches(isDisplayed()))
        onView(withId(R.id.product_rating)).check(matches(isDisplayed()))
    }

    private fun waitSleep(time: Long) {
        Thread.sleep(time)
    }

}