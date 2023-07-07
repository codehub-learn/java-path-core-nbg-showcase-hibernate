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
        entityManagerHQLOperations(entityManager);
        //entityManagerValidation(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void entityManagerHQLOperations(EntityManager entityManager) {
        List allProducts = entityManager
                .createQuery("SELECT pr FROM Product pr")
                .getResultList();
        logger.info("all products size: {}", allProducts.size());

        List allProducts2 = entityManager
                .createQuery("FROM Product")
                .getResultList();
        logger.info("all products size 2: {}", allProducts2.size());

        List orderItemByIdOne = entityManager
                .createQuery("SELECT oi FROM OrderItem oi WHERE oi.order.id = 1")
                .getResultList();
        logger.info("orderItems by id one: {}", orderItemByIdOne);

        List orderItemById2 = entityManager
                .createQuery("SELECT oi FROM OrderItem oi WHERE oi.order.id = :id")
                .setParameter("id", 1)
                .getResultList();
        logger.info("orderItems by id (2nd version): {}", orderItemById2);

        List productsByOrderId = entityManager
                .createQuery("SELECT pr FROM Product pr INNER JOIN OrderItem oi ON oi.product = pr WHERE oi.order.id = :id")
                .setParameter("id", 1)
                .getResultList();
        logger.info("productsByOrderId: {}", productsByOrderId);

        List multipleObjects = entityManager
                .createQuery("SELECT pr,oi FROM Product pr INNER JOIN OrderItem oi ON oi.product = pr WHERE oi.order.id = :id")
                .setParameter("id", 1)
                .getResultList();
        logger.info("mix: {}", multipleObjects.get(0));

        List customersByCustomerCategories = entityManager
                .createQuery("SELECT c FROM Customer c WHERE c.customerCategory IN :customerCategories")
                .setParameter("customerCategories", List.of(CustomerCategory.INDIVIDUAL, CustomerCategory.BUSINESS))
                .getResultList();
        logger.info("customersByCustomerCategories: {}", customersByCustomerCategories);

        List customersByCustomerCategories2 = entityManager
                .createNamedQuery("customersByCustomerCategories")
                .setParameter("customerCategories", List.of(CustomerCategory.INDIVIDUAL, CustomerCategory.BUSINESS))
                .getResultList();
        logger.info("customersByCustomerCategories (version 2): {}", customersByCustomerCategories2);

        entityManager
                .createQuery("SELECT c FROM Customer c WHERE c.firstname LIKE ?1")
                .setParameter(1, "io%")
                .getResultList();

        List maxPrice = entityManager
                .createQuery("SELECT MAX(pr.price) FROM Product pr")
                .getResultList();
        logger.info("max price: {}", maxPrice);

        List groupedBy = entityManager
                .createQuery("SELECT SUM(o.totalCost) FROM Customer c INNER JOIN Order o ON o.customer = c GROUP BY c.customerCategory")
                .getResultList();
        logger.info("grouping by:{}", groupedBy);

        entityManager.getTransaction().begin();
        int entitiesUpdated = entityManager
                .createQuery("UPDATE Customer c SET c.address = null WHERE c.customerCategory = 'INDIVIDUAL'")
                .executeUpdate();
        entityManager.getTransaction().commit();
        logger.info("entitiesUpdated: {}", entitiesUpdated);
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
        Customer customer2 = Customer.builder()
                .firstname("Ioannis2")
                .lastname("Daniil2")
                .email("idaniil2@gmail.com")
                .customerCategory(CustomerCategory.GOVERNMENT)
                .address("Athens 125th Av.")
                .age(55)
                .build();
        Customer customer3 = Customer.builder()
                .firstname("Ioannis3")
                .lastname("Daniil3")
                .email("idanii3l@gmail.com")
                .customerCategory(CustomerCategory.BUSINESS)
                .address("Athens 135th Av.")
                .age(55)
                .build();
        Customer customer4 = Customer.builder()
                .firstname("Ioannis4")
                .lastname("Daniil4")
                .email("idaniil4@gmail.com")
                .customerCategory(CustomerCategory.INDIVIDUAL)
                .address("Athens 145th Av.")
                .age(55)
                .build();
        save(customer, entityManager);
        save(customer2, entityManager);
        save(customer3, entityManager);
        save(customer4, entityManager);

        logger.info("{}", customer.getId());
        customer.setLastname("Daniel");
/*        save(customer, entityManager);
        save(customer, entityManager);
        save(customer, entityManager);
        save(customer, entityManager);
        save(customer, entityManager);
        save(customer, entityManager);*/

        Customer customer5 = Customer.builder().build();
        customer5.setId(2L);
        //save(customer5, entityManager);

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
                .price(BigDecimal.valueOf(55))
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