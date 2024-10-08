package dev.bujiku.batch_demo;

import org.springframework.data.repository.ListCrudRepository;

/**
 * @author Newton Bujiku
 * @since 2024
 */
public interface SalesInfoRepository extends ListCrudRepository<SalesInfo, Long> {
}
