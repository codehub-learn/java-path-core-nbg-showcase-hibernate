package gr.codelearn;

import gr.codelearn.model.Category;
import gr.codelearn.model.Customer;
import gr.codelearn.model.CustomerCategory;
import gr.codelearn.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MySQLModule");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer customer = Customer.builder()
                .firstname("Ioannis")
                .lastname("Daniil")
                .email("idaniil@gmail.com")
                .customerCategory(CustomerCategory.INDIVIDUAL)
                .address("Athens 15th Av.")
                .age(55)
                .build();
        save(customer, entityManager);
        logger.info("{}", customer.getId());
        customer.setLastname("Daniel");
        entityManager.clear();
/*        save(customer, entityManager);
        save(customer, entityManager);
        save(customer, entityManager);
        save(customer, entityManager);
        save(customer, entityManager);
        save(customer, entityManager);*/

        Customer customer2 = Customer.builder().build();
        customer2.setId(2L);
        //save(customer2, entityManager);

        Category category = Category.builder().description("GAMING KEYBOARDS").build();

        save(category, entityManager);

        Product product = Product.builder()
                .name("Gaming Keyboard 10536")
                .price(BigDecimal.TEN)
                .serial("AA12")
                .category(category)
                .build();

        save(product, entityManager);

        Product product2 = Product.builder()
                .name("Gaming Keyboard 10536")
                .price(BigDecimal.TEN)
                .serial("AA12")
                .category(category)
                .build();
        save(product2, entityManager);

        Product product3 = Product.builder()
                .name("Gaming Keyboard 10536")
                .price(BigDecimal.TEN)
                .serial("AA12")
                .category(category)
                .build();

        Product product4 = Product.builder()
                .name("Gaming Keyboard 10536")
                .price(BigDecimal.TEN)
                .build();

        save(product3, entityManager);
        save(product4, entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void save(Object entity, EntityManager entityManager) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            entityManager.getTransaction().rollback();
        }
    }

}