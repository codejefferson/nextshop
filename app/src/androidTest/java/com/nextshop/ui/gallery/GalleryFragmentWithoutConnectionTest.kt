package com.nextshop.ui.gallery

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nextshop.R
import com.nextshop.ui.ParentActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class GalleryFragmentWithoutConnectionTest {

    @get:Rule
    val parentActivityTestRule = ActivityTestRule(ParentActivity::class.java)

    @Test
    fun galleryScreen_showWithoutConnTest() {
        onView(withId(R.id.state_without_conn_gallery)).check(matches(isDisplayed()))

        onView(withId(R.id.state_progress_gallery)).check(matches(not(isDisplayed())))
        onView(withId(R.id.gallery_rv)).check(matches(not(isDisplayed())))
    }

}