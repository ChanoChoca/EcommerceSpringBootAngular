package com.chanochoca.ecom.product.infrastructure.secondary.repository;

import com.chanochoca.ecom.product.domain.aggregate.Picture;
import com.chanochoca.ecom.product.domain.aggregate.Product;
import com.chanochoca.ecom.product.domain.repository.ProductRepository;
import com.chanochoca.ecom.product.domain.vo.PublicId;
import com.chanochoca.ecom.product.infrastructure.secondary.entity.CategoryEntity;
import com.chanochoca.ecom.product.infrastructure.secondary.entity.PictureEntity;
import com.chanochoca.ecom.product.infrastructure.secondary.entity.ProductEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.Set;

@Repository
public class SpringDataProductRepository implements ProductRepository {

  private final JpaCategoryRepository jpaCategoryRepository;

  private final JpaProductRepository jpaProductRepository;

  private final JpaProductPictureRepository jpaProductPictureRepository;

  public SpringDataProductRepository(JpaCategoryRepository jpaCategoryRepository, JpaProductRepository jpaProductRepository, JpaProductPictureRepository jpaProductPictureRepository) {
    this.jpaCategoryRepository = jpaCategoryRepository;
    this.jpaProductRepository = jpaProductRepository;
    this.jpaProductPictureRepository = jpaProductPictureRepository;
  }

  @Override
  public Product save(Product productToCreate) {
    ProductEntity newProductEntity = ProductEntity.from(productToCreate);
    Optional<CategoryEntity> categoryEntityOpt = jpaCategoryRepository.findByPublicId(newProductEntity.getCategory().getPublicId());
    CategoryEntity categoryEntity = categoryEntityOpt.orElseThrow(() -> new EntityNotFoundException(String.format("No category found with Id %s", productToCreate.getCategory().getPublicId())));
    newProductEntity.setCategory(categoryEntity);
    ProductEntity savedProductEntity = jpaProductRepository.save(newProductEntity);

    saveAllPictures(productToCreate.getPictures(), savedProductEntity);

    return ProductEntity.to(savedProductEntity);
  }

  private void saveAllPictures(List<Picture> pictures, ProductEntity newProductEntity) {
    Set<PictureEntity> picturesEntities = PictureEntity.from(pictures);

    for (PictureEntity picturesEntity : picturesEntities) {
      picturesEntity.setProduct(newProductEntity);
    }

    jpaProductPictureRepository.saveAll(picturesEntities);
  }

  @Override
  public Page<Product> findAll(Pageable pageable) {
    return jpaProductRepository.findAll(pageable).map(ProductEntity::to);
  }

  @Override
  public int delete(PublicId publicId) {
    return jpaProductRepository.deleteByPublicId(publicId.value());
  }
}
