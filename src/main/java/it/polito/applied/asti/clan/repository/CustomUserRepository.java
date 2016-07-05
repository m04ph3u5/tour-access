package it.polito.applied.asti.clan.repository;

public interface CustomUserRepository {
	public int changePassword(String userEmail, String hashNewPassword);
}
