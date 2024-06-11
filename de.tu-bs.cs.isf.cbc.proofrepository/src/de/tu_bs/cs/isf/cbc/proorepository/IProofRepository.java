package de.tu_bs.cs.isf.cbc.proorepository;

import java.util.UUID;

public interface IProofRepository {
	
	String getPartialProofForId(UUID uuid);
	boolean savePartialProofForId(UUID uuid, String proof);
}
