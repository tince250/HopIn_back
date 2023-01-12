package com.hopin.HopIn.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hopin.HopIn.dtos.AllMessagesDTO;
import com.hopin.HopIn.dtos.AllNotesDTO;
import com.hopin.HopIn.dtos.AllUserRidesReturnedDTO;
import com.hopin.HopIn.dtos.AllUsersDTO;
import com.hopin.HopIn.dtos.CredentialsDTO;
import com.hopin.HopIn.dtos.MessageDTO;
import com.hopin.HopIn.dtos.MessageReturnedDTO;
import com.hopin.HopIn.dtos.NoteDTO;
import com.hopin.HopIn.dtos.NoteReturnedDTO;
import com.hopin.HopIn.dtos.TokenDTO;
import com.hopin.HopIn.dtos.UserReturnedDTO;
import com.hopin.HopIn.entities.User;

public interface IUserService {
	
	public User getById(int userId);
	
	public AllUsersDTO getAll(int page, int size);
	
	public TokenDTO login(CredentialsDTO credentials);

	public void block(int userId);
	
	public void unblock(int userId);
	
	public NoteReturnedDTO addNote(int userId, NoteDTO note);
	
	public AllNotesDTO getNotes(int userId, int page, int size);
	
	public MessageReturnedDTO sendMessage(int userId, MessageDTO sentMessage);
	
	public AllMessagesDTO getMessages(int userId);
	
	public AllUserRidesReturnedDTO getRides(int userId, int page, int size, String sort, String from, String to);

	public UserReturnedDTO getUser(int id);

	public Boolean userAlreadyExists(String email);

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

	public User getByEmail(String email);

	public void activateUser(User user);

	public User getCurrentUser();

	boolean isIdMatching(int id);

}