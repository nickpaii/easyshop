package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class MySqlProductDaoTest extends BaseDaoTestClass
{
    private MySqlProductDao dao;

    @BeforeEach
    public void setup()
    {
        dao = new MySqlProductDao(dataSource);
    }

    @Test
    public void getById_shouldReturn_theCorrectProduct()
    {
        // arrange
        int productId = 1;
        Product expected = new Product()
        {{
            setProductId(1);
            setName("Smartphone");
            setPrice(new BigDecimal("499.99"));
            setCategoryId(1);
            setDescription("A powerful and feature-rich smartphone for all your communication needs.");
            setColor("Black");
            setStock(50);
            setFeatured(false);
            setImageUrl("smartphone.jpg");
        }};

        // act
        var actual = dao.getById(productId);

        // assert
        assertEquals(expected.getPrice(), actual.getPrice(), "Because I tried to get product 1 from the database.");
    }

    @Test
    public void create_shouldReturn_createdProduct() {


        Product product = new Product();

                product.setName("Fake Product");
                product.setDescription("A totally real product. Not fake at all.");
                product.setCategoryId(1);
                product.setColor("");
                product.setPrice(new BigDecimal("400.00"));
                product.setStock(1);
                product.setFeatured(false);
                product.setImageUrl("");


        var actual = dao.create(product);

        assertEquals("Fake Product", actual.getName());
        assertEquals("A totally real product. Not fake at all.", actual.getDescription());
        assertEquals("", actual.getColor());
        assertEquals(1, actual.getStock());
        assertEquals(new BigDecimal("400.00"), actual.getPrice());
    }

    @Test
    public void update_shouldReturn_updatedProduct() {
        Product product = new Product();

            //Arrange
            product.setName("Laptop");
            product.setDescription("A high-performance laptop for work and entertainment.");
            product.setCategoryId(1);
            product.setColor("");
            product.setPrice(new BigDecimal("899.99"));
            product.setStock(1);
            product.setFeatured(false);
            product.setImageUrl("");

            Product savedProduct = dao.create(product);

            int savedProductId = savedProduct.getProductId();

            //Act
            Product updatedProduct = new Product();

            updatedProduct.setName("Laptop");
            updatedProduct.setDescription("gaming laptop");
            updatedProduct.setCategoryId(1);
            updatedProduct.setColor("");
            updatedProduct.setPrice(new BigDecimal("999.99"));
            updatedProduct.setStock(1);
            updatedProduct.setFeatured(false);
            updatedProduct.setImageUrl("");

            dao.update(savedProductId, updatedProduct);

            //Assert

            Product verify = dao.getById(savedProductId);
            assertEquals(new BigDecimal("999.99"), verify.getPrice());
            assertEquals("gaming laptop", verify.getDescription());
            assertEquals(savedProductId, verify.getProductId());

    }

}