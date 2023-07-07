package gr.codelearn;

import gr.codelearn.model.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MySQLModule");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManagerBasicOperations(entityManager);
        entityManagerValidation(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void entityManagerBasicOperations(EntityManager entityManager) {
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

        Product product2 = Product.builder()
                .name("Gaming Keyboard 32526732")
                .price(BigDecimal.TEN)
                .serial("AA11")
                .category(category)
                .build();

        Product product3 = Product.builder()
                .name("Gaming Keyboard 743723467")
                .price(BigDecimal.TEN)
                .serial("AA13")
                .category(category)
                .build();

        Product product4 = Product.builder()
                .name("Gaming Keyboard 2362363")
                .price(BigDecimal.TEN)
                .serial("AA15")
                .category(category)
                .build();

        save(product, entityManager);
        save(product2, entityManager);
        save(product3, entityManager);
        save(product4, entityManager);


        Order order = Order.builder()
                .customer(customer)
                .submitDate(new Date())
                .totalCost(BigDecimal.valueOf(50))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .build();

        List<OrderItem> orderItems = List.of(
                OrderItem.builder().quantity(1).price(BigDecimal.valueOf(10)).order(order).product(product).build(),
                OrderItem.builder().quantity(2).price(BigDecimal.valueOf(10)).order(order).product(product2).build(),
                OrderItem.builder().quantity(3).price(BigDecimal.valueOf(20)).order(order).product(product3).build(),
                OrderItem.builder().quantity(4).price(BigDecimal.valueOf(10)).order(order).product(product4).build()
        );

        order.setOrderItems(orderItems);
        save(order, entityManager);
        //remove(order, entityManager);
        //remove(category, entityManager);

        entityManager.clear();
        Customer foundCustomer = entityManager.find(Customer.class, 1);
        Order foundOrder = entityManager.find(Order.class, 1);

        foundOrder.getOrderItems().remove(0);
        save(foundOrder, entityManager);
        //logger.info("orderitem size:{}", foundOrder.getOrderItems().size());
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

    private static void entityManagerValidation(EntityManager entityManager){
        Category nullCategory = Category.builder().build();
        Validator validator = Validation.byDefaultProvider().configure().messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory().getValidator();
        Set<ConstraintViolation<Category>> validation = validator.validate(nullCategory);
        logger.info("{}",validation);
        //save(nullCategory, entityManager);
    }

    private static void remove(Object entity, EntityManager entityManager) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            entityManager.getTransaction().rollback();
        }
    }

}