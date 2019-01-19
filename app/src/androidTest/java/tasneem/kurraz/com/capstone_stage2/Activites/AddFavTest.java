package tasneem.kurraz.com.capstone_stage2.Activites;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import tasneem.kurraz.com.capstone_stage2.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddFavTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);

    @Test
    public void addFavTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction viewPager = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager.perform(swipeLeft());

        ViewInteraction viewPager2 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager2.perform(swipeLeft());

        ViewInteraction viewPager3 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager3.perform(swipeLeft());

        ViewInteraction viewPager4 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager4.perform(swipeLeft());

        ViewInteraction viewPager5 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager5.perform(swipeRight());

        ViewInteraction viewPager6 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager6.perform(swipeLeft());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction viewPager7 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager7.perform(swipeLeft());

        ViewInteraction viewPager8 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager8.perform(swipeLeft());

        ViewInteraction viewPager9 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager9.perform(swipeLeft());

        ViewInteraction viewPager10 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager10.perform(swipeRight());

        ViewInteraction viewPager11 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager11.perform(swipeLeft());

        ViewInteraction viewPager12 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager12.perform(swipeLeft());

        ViewInteraction viewPager13 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager13.perform(swipeLeft());

        ViewInteraction viewPager14 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager14.perform(swipeLeft());

        ViewInteraction viewPager15 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager15.perform(swipeRight());

        ViewInteraction viewPager16 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager16.perform(swipeLeft());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction viewPager17 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager17.perform(swipeLeft());

        ViewInteraction viewPager18 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager18.perform(swipeLeft());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction viewPager19 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager19.perform(swipeLeft());

        ViewInteraction viewPager20 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager20.perform(swipeRight());

        ViewInteraction viewPager21 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager21.perform(swipeRight());

        ViewInteraction viewPager22 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager22.perform(swipeRight());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.products_recycle),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)));

        ViewInteraction viewPager23 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager23.perform(swipeLeft());

        ViewInteraction viewPager24 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager24.perform(swipeRight());

        ViewInteraction viewPager25 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager25.perform(swipeRight());

        ViewInteraction viewPager26 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager26.perform(swipeRight());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.add_btn), withText("+"),
                        childAtPosition(
                                allOf(withId(R.id.layout),
                                        childAtPosition(
                                                withId(R.id.pro_quantity),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction viewPager27 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager27.perform(swipeLeft());

        ViewInteraction viewPager28 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager28.perform(swipeRight());

        ViewInteraction viewPager29 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager29.perform(swipeRight());

        ViewInteraction viewPager30 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager30.perform(swipeRight());

        ViewInteraction viewPager31 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager31.perform(swipeLeft());

        ViewInteraction viewPager32 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager32.perform(swipeRight());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.addTofavDetails), withText("add to Favorite"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction viewPager33 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager33.perform(swipeRight());

        ViewInteraction viewPager34 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager34.perform(swipeRight());

        ViewInteraction viewPager35 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager35.perform(swipeLeft());

        ViewInteraction viewPager36 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager36.perform(swipeRight());

        ViewInteraction viewPager37 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager37.perform(swipeRight());

        ViewInteraction viewPager38 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager38.perform(swipeRight());

        ViewInteraction viewPager39 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager39.perform(swipeLeft());

        ViewInteraction viewPager40 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager40.perform(swipeRight());

        ViewInteraction viewPager41 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager41.perform(swipeRight());

        ViewInteraction viewPager42 = onView(
                allOf(withId(R.id.vp_slider_layout),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.imageSlider),
                                        0),
                                0),
                        isDisplayed()));
        viewPager42.perform(swipeRight());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
