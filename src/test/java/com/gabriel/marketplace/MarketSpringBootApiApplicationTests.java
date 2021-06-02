package com.gabriel.marketplace;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.springframework.util.Assert.isTrue;

@SpringBootTest
class MarketSpringBootApiApplicationTests {
    
    public boolean isPersonUnderAge(int number){
        return number>17;
    }

    @Test
    void myTestVerifyNumbers() {
        int myNumber = 18;
        boolean result = isPersonUnderAge(myNumber);
        Assert.isTrue(result);
    }


}
