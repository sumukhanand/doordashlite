package com.sumukha.doordashlite;

import com.sumukha.doordashlite.data.data_sources.RemoteDataSource;
import com.sumukha.doordashlite.data.models.Restaurant;
import com.sumukha.doordashlite.ui.presenters.DiscoverPresenter;
import com.sumukha.doordashlite.ui.views.DiscoverView;
import com.sumukha.doordashlite.util.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.android.plugins.RxAndroidPlugins.setInitMainThreadSchedulerHandler;
import static io.reactivex.plugins.RxJavaPlugins.setComputationSchedulerHandler;
import static io.reactivex.plugins.RxJavaPlugins.setIoSchedulerHandler;
import static io.reactivex.plugins.RxJavaPlugins.setNewThreadSchedulerHandler;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DiscoverPresenterTest {

    class TestError extends Error {
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    RemoteDataSource mockDataManager;

    @Mock
    DiscoverView mockView;

    DiscoverPresenter presenter;

    @BeforeClass
    public static void setUpClass() {
        setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        presenter = new DiscoverPresenter(mockView, mockDataManager);
    }

    @After
    public void tearDown() {
        presenter.onDestroy();
    }

    @Test
    public void successTest() {
        List<Restaurant> restaurants = new ArrayList<>();
        when(mockDataManager.fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG)).thenReturn(Observable.just(restaurants));

        presenter.fetchData();
        verify(mockDataManager).fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG);
        verify(mockView).onFetchDataSuccess(restaurants);
    }

    @Test
    public void failTest() {
        TestError error = new TestError();
        when(mockDataManager.fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG)).thenReturn(Observable.error(error));

        presenter.fetchData();
        verify(mockDataManager).fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG);
        verify(mockView).onFetchDataError(error);
    }

}
