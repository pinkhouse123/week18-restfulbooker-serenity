package com.herokuapp.restful_booker.gorestinfo;

import com.herokuapp.restful_booker.bookinfo.BookInfoTest;
import com.herokuapp.restful_booker.testbase.TestBase;
import com.herokuapp.restful_booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class BookCurdTest extends TestBase {
    static String name = "Umi" + TestUtils.getRandomValue();
    static String lastname = "Patel";
    static int price = 30;
    static boolean result = true;

    static HashMap<String, String> dates;
    static String needs = "Breakfast";
    static int bookingId;
    @Steps
    BookInfoTest bookInfoTest;

    @Title("creating booking ")
    @Test
    public void test001() {
        dates = new HashMap<>();
        dates.put("checkin", "2019-10-01");
        dates.put("checkout", "2019-20-01");
        ValidatableResponse response = bookInfoTest.createBooking(name, lastname, price, result, dates, needs);
        response.log().all().statusCode(200);

        bookingId = response.extract().path("bookingid");
        System.out.println(bookingId);
    }
    @Title("getting single data")
    @Test
    public void test002(){
        ValidatableResponse response = bookInfoTest.getingSingleBooking(bookingId);
        response.log().all().statusCode(200);

        response.body("firstname",equalTo(name));

    }
    @Title("updating booking ")
    @Test
    public void test003() {
        name=name+"updated001";
        dates = new HashMap<>();
        dates.put("checkin", "2019-10-01");
        dates.put("checkout", "2019-20-01");
        ValidatableResponse response = bookInfoTest.updatingBooking(bookingId,name, lastname,price, result, dates, needs);
        response.log().all().statusCode(200);


    }
    @Title("deleting booking")
    @Test
    public void test004(){
        ValidatableResponse response = bookInfoTest.deleteSingleBooking(bookingId);
        response.log().all().statusCode(201);

        ValidatableResponse response1 = bookInfoTest.getingSingleBooking(bookingId);
        response1.log().all().statusCode(404);

    }
}