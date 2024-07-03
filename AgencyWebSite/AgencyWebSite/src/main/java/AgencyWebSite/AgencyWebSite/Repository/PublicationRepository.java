package AgencyWebSite.AgencyWebSite.Repository;

import AgencyWebSite.AgencyWebSite.Models.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
