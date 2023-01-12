package com.green.shopping;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootTest
@EnableMongoRepositories
class ShoppingApplicationTests {

	@Test
	void contextLoads() {
	}

}
