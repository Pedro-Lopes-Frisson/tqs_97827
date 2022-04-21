package tqs.PredroLopes.TestContainersLab7_3;


import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
