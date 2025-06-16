package com.djamo.qa.tests;

import java.io.FileReader;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.djamo.qa.base.BaseTest;
import com.djamo.qa.pages.SearchAndNavigatePage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class SearchAndNavigateTest extends BaseTest {
	

    private SearchAndNavigatePage page;

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() throws Exception {
        JsonArray array = JsonParser.parseReader(
                new FileReader("src/test/resources/data/search_route.json")
        ).getAsJsonArray();

        Gson gson = new Gson();
        Object[][] data = new Object[array.size()][1];
        for (int i = 0; i < array.size(); i++) {
            data[i][0] = gson.fromJson(array.get(i), Map.class);
        }
        return data;
    }

    @Test(dataProvider = "searchData", retryAnalyzer = com.djamo.qa.utils.Retry.class)
    public void testSearchAndNavigate(Map<String, String> data) {
        page = new SearchAndNavigatePage(driver);

        String searchText = data.get("searchText");
        String startPoint = data.get("startPoint");
        
        page.dismissSignInPopupIfPresent();
        page.dismissSignInPopupIfPresent();
        page.openSearchBox();
        page.enterSearchText(searchText);
        page.tapOnDirectionsButton();
        page.changeStartingPoint(startPoint);
        Assert.assertTrue(page.isMapVisible(), "The map should be visible after starting the direction");
        
        page.captureMapScreenshotIfVisible(driver);
    }

}
