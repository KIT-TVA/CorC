package de.tu_bs.cs.isf.cbc.proofrepository;

import java.util.List;
import java.util.UUID;

public interface IProofRepository {
	
	void getPartialProofForId(List<String> featureNames, List<UUID> uuids, String location, String proofFileName);
	boolean savePartialProofForId(List<String> featureNames, List<UUID> uuids, String proof);
}
