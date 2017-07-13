package com.myntra.coachmarks.ui.presenter;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.Log;

import com.myntra.coachmarks.PopUpCoachMark;
import com.myntra.coachmarks.builder.CoachMarkBuilder;
import com.myntra.coachmarks.builder.CoachMarkLayoutMargin;
import com.myntra.coachmarks.builder.CoachMarkPixelInfo;
import com.myntra.coachmarks.builder.ImageLayoutInformation;
import com.myntra.coachmarks.builder.InfoForViewToMask;
import com.myntra.coachmarks.common.CoachMarkAlignPosition;
import com.myntra.coachmarks.common.CoachMarkTextGravity;
import com.myntra.coachmarks.common.DialogDismissButtonPosition;
import com.myntra.coachmarks.common.PopUpPosition;
import com.myntra.coachmarks.providers.interfaces.IDimensionResourceProvider;
import com.myntra.coachmarks.providers.interfaces.IScreenInfoProvider;
import com.myntra.coachmarks.providers.interfaces.IStringResourceProvider;
import com.myntra.coachmarks.providers.interfaces.ITypeFaceProvider;
import com.myntra.coachmarks.ui.presentation.IPopUpCoachMarkPresentation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PopUpCoachMarkPresenter.class,Log.class})
public class PopUpCoachMarkPresenterTest {

    @Mock
    CoachMarkBuilder mCoachMarkBuilder;

    @Mock
    IPopUpCoachMarkPresentation mPopUpCoachMarkPresentation;

    @Mock
    IStringResourceProvider mStringResourceProvider;

    @Mock
    IDimensionResourceProvider mDimensionResourceProvider;

    @Mock
    IScreenInfoProvider mScreenInfoProvider;

    @Mock
    ITypeFaceProvider mTypeFaceProvider;

    @Mock
    @StringRes
    int mockedStringRes;

    @Mock
    @ColorRes
    int mockedColorRes;

    @Mock
    @DrawableRes
    int mockedDrawableRes;

    private PopUpCoachMarkPresenter mPopUpCoachMarkPresenter;
    private CoachMarkPixelInfo mCoachMarkPixelInfo;

    @Before
    public void setUp() throws Exception {
        mPopUpCoachMarkPresenter = new PopUpCoachMarkPresenter(mStringResourceProvider,
                mDimensionResourceProvider, mTypeFaceProvider, mScreenInfoProvider);

        mPopUpCoachMarkPresenter.onCreate(mCoachMarkBuilder);
        mPopUpCoachMarkPresenter.onCreateView(mPopUpCoachMarkPresentation);

        mCoachMarkPixelInfo = CoachMarkPixelInfo.create()
                .setMarginRectInPixels(new Rect(10, 10, 10, 10))
                .setPopUpHeightInPixels(50)
                .setPopUpHeightInPixelsWithOffset(70)
                .setPopUpWidthInPixels(150)
                .setPopUpWidthInPixelsWithOffset(170)
                .setWidthHeightOffsetForCoachMarkPopUp(10)
                .setActionBarHeightPixels(10)
                .setFooterHeightPixels(10)
                .setNotchDimenInPixels(25)
                .setMarginOffsetForNotchInPixels(5)
                .setImageHeightInPixels(30)
                .setImageWidthInPixels(30)
                .setScreenHeightInPixels(1024)
                .setScreenWidthInPixels(768)
                .build();
    }

    @Test
    public void onCreateTest(){
        PopUpCoachMarkPresenter popUpCoachMarkPresenter = Mockito.mock(PopUpCoachMarkPresenter.class);
        popUpCoachMarkPresenter.onCreate(mCoachMarkBuilder);
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void onCreateViewTest(){
        PopUpCoachMarkPresenter popUpCoachMarkPresenter = Mockito.mock(PopUpCoachMarkPresenter.class);
        popUpCoachMarkPresenter.onCreateView(mPopUpCoachMarkPresentation);
        Mockito.verify(popUpCoachMarkPresenter, Mockito.times(1)).onCreateView(mPopUpCoachMarkPresentation);
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void onDestroyTest(){
        PopUpCoachMarkPresenter popUpCoachMarkPresenter = Mockito.mock(PopUpCoachMarkPresenter.class);
        popUpCoachMarkPresenter.onDestroy();
        Mockito.verify(popUpCoachMarkPresenter, Mockito.times(1)).onDestroy();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void onDestroyViewTest() {
        PopUpCoachMarkPresenter popUpCoachMarkPresenter = Mockito.mock(PopUpCoachMarkPresenter.class);
        popUpCoachMarkPresenter.onDestroyView();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void onOkButtonClicked() throws Exception {
        mPopUpCoachMarkPresenter.onOkButtonClicked();
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).closeCoachMarkDialog();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void onShimClicked() throws Exception {
        mPopUpCoachMarkPresenter.onShimClicked();
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).closeCoachMarkDialog();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void setMessageForCoachMarkTextTest() {
        Mockito.when(mStringResourceProvider.getStringFromResource(mockedStringRes)).thenReturn("MockedString");
        mPopUpCoachMarkPresenter.setMessageForCoachMarkText(mockedStringRes);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).setCoachMarkMessage("MockedString");
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void setTypeFaceForDismissButtonNullFontFileTest() {
        mPopUpCoachMarkPresenter.setTypeFaceForDismissButton(null);
        Mockito.verify(mTypeFaceProvider, Mockito.times(0)).getTypeFaceFromAssets(Matchers.anyString());
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(0))
                .setTypeFaceForDismissButton((Typeface) Matchers.anyObject());
    }

    @Test
    public void setTypeFaceForDismissButtonNullTypeFaceTest() {
        Mockito.when(mTypeFaceProvider.getTypeFaceFromAssets(Matchers.anyString())).thenReturn(null);

        mPopUpCoachMarkPresenter.setTypeFaceForDismissButton(Matchers.anyString());

        Mockito.verify(mTypeFaceProvider, Mockito.times(1)).getTypeFaceFromAssets(Matchers.anyString());
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(0))
                .setTypeFaceForDismissButton((Typeface) Matchers.anyObject());

        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void setTypeFaceForDismissButtonTest() {

        Typeface typeface = Mockito.mock(Typeface.class);

        Mockito.when(mTypeFaceProvider.getTypeFaceFromAssets(Matchers.anyString())).thenReturn(typeface);

        mPopUpCoachMarkPresenter.setTypeFaceForDismissButton("fontFilePath");
        Mockito.verify(mTypeFaceProvider, Mockito.times(1)).getTypeFaceFromAssets(Matchers.anyString());
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1))
                .setTypeFaceForDismissButton(typeface);

        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void setTypeFaceForCoachMarkTextNullFontFileTest() {
        mPopUpCoachMarkPresenter.setTypeFaceForCoachMarkText(null);
        Mockito.verify(mTypeFaceProvider, Mockito.times(0)).getTypeFaceFromAssets(Matchers.anyString());
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(0))
                .setTypeFaceForPopUpText((Typeface) Matchers.anyObject());
    }

    @Test
    public void setTypeFaceForCoachMarkTextNullTypefaceTest() {
        Mockito.when(mTypeFaceProvider.getTypeFaceFromAssets(Matchers.anyString())).thenReturn(null);

        mPopUpCoachMarkPresenter.setTypeFaceForCoachMarkText("fontFilePath");

        Mockito.verify(mTypeFaceProvider, Mockito.times(1)).getTypeFaceFromAssets("fontFilePath");
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(0))
                .setTypeFaceForPopUpText((Typeface) Matchers.anyObject());

        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void setTypeFaceForCoachMarkTextTest() {
        Typeface typeface = Mockito.mock(Typeface.class);

        Mockito.when(mTypeFaceProvider.getTypeFaceFromAssets(Matchers.anyString())).thenReturn(typeface);

        mPopUpCoachMarkPresenter.setTypeFaceForCoachMarkText("fontFilePath");
        Mockito.verify(mTypeFaceProvider, Mockito.times(1)).getTypeFaceFromAssets(Matchers.anyString());
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1))
                .setTypeFaceForPopUpText(typeface);

        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void setGravityForCoachMarkTextLeftGravityTest() {
        mPopUpCoachMarkPresenter.setGravityForCoachMarkText(CoachMarkTextGravity.CENTER);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1))
                .setUpGravityForCoachMarkText(CoachMarkTextGravity.CENTER);
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void setGravityForCoachMarkTextRightGravityTest() {
        mPopUpCoachMarkPresenter.setGravityForCoachMarkText(CoachMarkTextGravity.RIGHT);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1))
                .setUpGravityForCoachMarkText(CoachMarkTextGravity.RIGHT);
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void setGravityForCoachMarkTextCenterGravityTest() {
        mPopUpCoachMarkPresenter.setGravityForCoachMarkText(CoachMarkTextGravity.LEFT);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1))
                .setUpGravityForCoachMarkText(CoachMarkTextGravity.LEFT);
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void getMarginLeftForNotchTest() {
        assertEquals(140, mPopUpCoachMarkPresenter.getMarginLeftForNotch(.75, 190, 50));
        assertEquals(25, mPopUpCoachMarkPresenter.getMarginLeftForNotch(.25, 100, 25));
    }

    @Test
    public void detectAndCreateShimOutViewsNullShimOutViewListTest() {
        mPopUpCoachMarkPresenter.detectAndCreateShimOutViews(null);
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(0)).
                createViewToBeMaskedOut(Matchers.anyInt(), Matchers.anyInt(),
                        Matchers.anyInt(), Matchers.anyInt());
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void detectAndCreateShimOutViewsTest() {

        int listSize = 2;
        List<InfoForViewToMask> infoForViewToMaskList = new ArrayList<>(listSize);

        InfoForViewToMask infoForViewToMask = InfoForViewToMask.create(new Point(0, 0),
                100, 100).build();

        for (int i = 0; i < listSize; i++) {
            infoForViewToMaskList.add(infoForViewToMask);
        }

        mPopUpCoachMarkPresenter.detectAndCreateShimOutViews(infoForViewToMaskList);
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(listSize)).
                createViewToBeMaskedOut(Matchers.anyInt(), Matchers.anyInt(),
                        Matchers.anyInt(), Matchers.anyInt());
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void setImageParamsAndPositionTest(){
        Point anchorTop = new Point(0,0);
        Point anchorBotom = new Point(100, 100);

        mPopUpCoachMarkPresenter.setImageParamsAndPosition(anchorTop, anchorBotom,
                30, 30, mockedColorRes, mockedDrawableRes);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).setImageInformation(Matchers.anyDouble(),
                Matchers.anyDouble(),Matchers.anyInt(), Matchers.anyInt(),Matchers.anyInt(), Matchers.anyInt());
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void showCoachMarkPositionLeftTest(){
        mPopUpCoachMarkPresenter.showCoachMark(DialogDismissButtonPosition.LEFT, PopUpPosition.LEFT);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).
                setPopUpViewPositionWithRespectToImage(CoachMarkAlignPosition.ALIGN_RIGHT);
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).setDismissButtonPositionLeft();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void showCoachMarkPositionRightTest(){
        mPopUpCoachMarkPresenter.showCoachMark(DialogDismissButtonPosition.LEFT, PopUpPosition.RIGHT);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).
                setPopUpViewPositionWithRespectToImage(CoachMarkAlignPosition.ALIGN_LEFT);
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).setDismissButtonPositionLeft();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void showCoachMarkPositionTopTest(){
        mPopUpCoachMarkPresenter.showCoachMark(DialogDismissButtonPosition.LEFT, PopUpPosition.TOP);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).
                setPopUpViewPositionWithRespectToImage(CoachMarkAlignPosition.ALIGN_BOTTOM);
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).setDismissButtonPositionLeft();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void showCoachMarkPositionBottomTest(){
        mPopUpCoachMarkPresenter.showCoachMark(DialogDismissButtonPosition.LEFT, PopUpPosition.BOTTOM);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).
                setPopUpViewPositionWithRespectToImage(CoachMarkAlignPosition.ALIGN_TOP);
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).setDismissButtonPositionLeft();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void showCoachMarkPositionNoneTest(){
        PowerMockito.mockStatic(Log.class);
        PowerMockito.when(Log.wtf(Matchers.anyString(), Matchers.anyString())).thenReturn(1);

        mPopUpCoachMarkPresenter.showCoachMark(DialogDismissButtonPosition.LEFT, PopUpPosition.NONE);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(0)).
                setPopUpViewPositionWithRespectToImage(CoachMarkAlignPosition.ALIGN_TOP);
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(0)).setDismissButtonPositionLeft();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);

    }

    @Test
    public void showCoachMarkPositionTopDialogDismissButtonRightTest(){
        mPopUpCoachMarkPresenter.showCoachMark(DialogDismissButtonPosition.RIGHT, PopUpPosition.TOP);

        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).
                setPopUpViewPositionWithRespectToImage(CoachMarkAlignPosition.ALIGN_BOTTOM);
        Mockito.verify(mPopUpCoachMarkPresentation, Mockito.times(1)).setDismissButtonPositionRight();
        Mockito.verifyNoMoreInteractions(mPopUpCoachMarkPresentation);
    }

    @Test
    public void checkIfLeftPossibleTest(){

        MockedPoint point = new MockedPoint(600, 300);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfLeftPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(400, 200);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfLeftPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(100, 200);
        assertEquals(false, mPopUpCoachMarkPresenter.checkIfLeftPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(700, 700);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfLeftPossible(point, mCoachMarkPixelInfo));

    }

    @Test
    public void checkIfRightPossibleTest(){

        MockedPoint point = new MockedPoint(600, 300);
        assertEquals(false, mPopUpCoachMarkPresenter.checkIfRightPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(400, 200);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfRightPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(100, 200);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfRightPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(700, 700);
        assertEquals(false, mPopUpCoachMarkPresenter.checkIfRightPossible(point, mCoachMarkPixelInfo));

    }

    @Test
    public void checkIfTopPossibleTest(){

        MockedPoint point = new MockedPoint(600, 300);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfTopPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(400, 200);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfTopPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(100, 200);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfTopPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(700, 700);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfTopPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(400, 50);
        assertEquals(false, mPopUpCoachMarkPresenter.checkIfTopPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(0, 50);
        assertEquals(false, mPopUpCoachMarkPresenter.checkIfTopPossible(point, mCoachMarkPixelInfo));

    }

    @Test
    public void checkIfBottomPossibleTest(){

        MockedPoint point = new MockedPoint(600, 300);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfBottomPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(400, 200);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfBottomPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(100, 200);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfBottomPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(700, 700);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfBottomPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(400, 50);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfBottomPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(0, 50);
        assertEquals(true, mPopUpCoachMarkPresenter.checkIfBottomPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(0, 1000);
        assertEquals(false, mPopUpCoachMarkPresenter.checkIfBottomPossible(point, mCoachMarkPixelInfo));

        point = new MockedPoint(700, 1020);
        assertEquals(false, mPopUpCoachMarkPresenter.checkIfBottomPossible(point, mCoachMarkPixelInfo));
    }

    @Test
    public void getCorrectPositionOfCoachMarkIfDefaultFailsRightPositionTest(){

        MockedPoint point = new MockedPoint(400, 200);
        assertEquals(PopUpPosition.RIGHT, mPopUpCoachMarkPresenter.
                getCorrectPositionOfCoachMarkIfDefaultFails(point, mCoachMarkPixelInfo));


    }

    @Test
    public void getCorrectPositionOfCoachMarkIfDefaultFailsLeftPositionTest(){

        MockedPoint point = new MockedPoint(700, 1020);
        assertEquals(PopUpPosition.LEFT, mPopUpCoachMarkPresenter.
                getCorrectPositionOfCoachMarkIfDefaultFails(point, mCoachMarkPixelInfo));

    }

    @Test
    public void getCorrectPositionOfCoachMarkIfDefaultFailsBottomPositionTest(){

        MockedPoint point = new MockedPoint(700, 820);
        assertEquals(PopUpPosition.BOTTOM, mPopUpCoachMarkPresenter.
                getCorrectPositionOfCoachMarkIfDefaultFails(point, mCoachMarkPixelInfo));

    }

    @Test
    public void getActualTopMarginTest(){
        assertEquals(100, mPopUpCoachMarkPresenter.getActualTopMargin(100, mCoachMarkPixelInfo));
        assertEquals(900, mPopUpCoachMarkPresenter.getActualTopMargin(900, mCoachMarkPixelInfo));
    }

    @Test
    public void getActualLeftMarginTest(){
        assertEquals(400, mPopUpCoachMarkPresenter.getActualTopMargin(400, mCoachMarkPixelInfo));
        assertEquals(800, mPopUpCoachMarkPresenter.getActualTopMargin(800, mCoachMarkPixelInfo));
    }

    //Fake class to mock Point behaviour for junit tests
    public static class MockedPoint extends Point {
        MockedPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}