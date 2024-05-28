package de.tu_bs.cs.isf.cbc.tool;

import java.util.UUID;

public interface IProofRepository {
	
	String getPartialProofForId(UUID uuid);
	boolean savePartialProofForId(UUID uuid, String proof);
}
