package yatospace.user.program;

import java.util.List;
import java.util.Scanner;

import yatospace.user.controller.DirectCredentialController;
import yatospace.user.controller.UserCredentialsController;
import yatospace.user.object.User;

/**
 * Симулација корисничког регистра.
 * @author MV
 * @version 1.0
 */
public class JavaUserCredentialSimulator {
	public static final Scanner scanner = new Scanner(System.in);
	public static final DirectCredentialController controller = new DirectCredentialController();
	
	public static void main(String ... args) {
		System.out.println("Dobrodosli.");
		while(true) {
			menu(); 
			int izbor = choose();
			System.out.println(); 
			if(izbor==0) break;
			execute(izbor);
		}
		System.out.println("Dovidjenja.");
	}
	
	public static void menu() {
		System.out.println();
		System.out.println("0. Izlaz");
		System.out.println("1. Dodavanje");
		System.out.println("2. Brisanje");
		System.out.println("3. Brojanje");
		System.out.println("4. Pregled"); 
		System.out.println("5. Provjera sifre");  
		System.out.println("6. Promjena korisnickog imena");
		System.out.println("7. Promjera sifre");
		System.out.println(); 
	}
	
	public static int choose() {
		int izbor = -1; 
		try {
			System.out.print("Izbor : ");
			izbor = Integer.parseInt(scanner.nextLine()); 
			return izbor;
		}catch(Exception ex) {
			return -1; 
		}
	}
	
	public static void execute(int izbor) {
		switch(izbor) {
			case 1: 
				System.out.println("Dodavanje.");
				add(); 
				break; 
			case 2: 
				System.out.println("Brisanje.");
				remove();
				break; 
			case 3: 
				System.out.println("Brojanje.");
				count(); 
				break;
			case 4: 
				System.out.println("Pregled."); 
				list(); 
				break; 
			case 5: 
				System.out.println("Provjera lozinke."); 
				check(); 
				break;
			case 6: 
				System.out.println("Promjena korisnckog imena."); 
				updateUsername(); 
				break; 
			case 7:
				System.out.println("Promjena lozinke"); 
				updatePassword();
				break;
			default: 
				System.out.println("Pogresan izbor.");
				break; 
		}
	}
	
	public static void add() {
		System.out.println(); 
		System.out.print("Korisnicko ime : ");
		String username = scanner.nextLine(); 
		if(controller.testContains(username)) {
			System.out.println("Korisnik nije unesen jer postoji.");
			return; 
		}
		UserCredentialsController controll = new UserCredentialsController(); 
		controll.setSaltGeneratorTool(controller.getSaltgenerator());
		controll.setUser(new User(username)); 
		boolean passwordOK = false; 
		String password = ""; 
		do {
			System.out.print("Unesi lozinku : ");
			password = scanner.nextLine();
			if(controll.setGoodPassword(password).length()>0) {
				passwordOK=true;
				controll.getCredentialsTool().hashPasswordToAll().resetPasswordPlain(); 
			}
		}while(!passwordOK);
		controller.add(username, password);
		System.out.println("Dodan je korisnik "+username+" sa zapisom sifre "+password); 
	}
	
	public static void remove() {
		System.out.println(); 
		System.out.print("Korisnicko ime : ");
		String username = scanner.nextLine(); 
		controller.remove(username);
		System.out.println("Korisnik "+username+" je izbrisan.");
	}
	
	public static void count() {
		System.out.println(); 
		System.out.println("Postoji "+controller.count()+" korisnika u registru."); 
	}
	
	public static void list() {
		try {
			System.out.println(); 
			System.out.print("Unesi velicinu stranice (korag stranicenja) : "); 
			int pageSize = Integer.parseInt(scanner.nextLine());
			System.out.print("Unesi trazenu stranicu (pocev od 0) : "); 
			int pageNo = Integer.parseInt(scanner.nextLine());
			System.out.print("Unesi na koju pocetak ide korisnicko ime : ");
			String startFilter = scanner.nextLine(); 
			List<User> users = controller.list(pageNo, pageSize, startFilter); 
			if(users.size()>0) System.out.println();
			for(User user: users) 
				System.out.println(user.getUsername()+" "+controller.getController(user.getUsername()).getCredentialsTool().getPasswordRecord());
		}catch(Exception ex) {
			System.out.println("Greska pri unosu indeksa.");
		}
	}
	
	public static void check() {
		System.out.println(); 
		System.out.print("Korisnicko ime : ");
		String username = scanner.nextLine();
		
		UserCredentialsController controll = controller.getController(username); 
		if(controll==null) {System.out.println("Kontroler korisnika ne postoji, zbog nepostojanja korisnika.");  return; }
		
		System.out.print("Lozinka : ");
		String password = scanner.nextLine();
		
		boolean result = controll.checkPassword(password);
		if(result) System.out.println("Sifra je odgovarajuca.");
		else System.out.println("Sifra nije odgovarajuca."); 
		
		controll.getCredentialsTool().resetPasswordPlain(); 
	}
	
	public static void updateUsername() {
		System.out.println(); 
		System.out.print("Korisnicko ime : ");
		String username = scanner.nextLine();
		
		UserCredentialsController controll = controller.getController(username); 
		if(controll==null) {System.out.println("Kontroler korisnika ne postoji, zbog nepostojanja korisnika.");  return; }
		
		System.out.print("Novo korisnicko ime: ");
		String newUsername = scanner.nextLine();
		
		UserCredentialsController newController = controller.getController(newUsername);
		if(newController!=null) {System.out.println("Novo korisnicko ime je zauzeto.");  return; }
		
		controller.updateUsername(username, newUsername); 
		System.out.println("Preimenovnovanje korisnika "+username+" na "+newUsername+" uspjesno.");
	}
	
	public static void updatePassword() {
		System.out.println(); 
		System.out.print("Korisnicko ime : ");
		String username = scanner.nextLine();
		
		UserCredentialsController controll = controller.getController(username); 
		if(controll==null) {System.out.println("Kontroler korisnika ne postoji, zbog nepostojanja korisnika.");  return; }
		
		System.out.print("Nova lozinka : ");
		String password = scanner.nextLine();
		
		controller.updatePassword(username, password); 		
		System.out.println("Nova lozinka za korisnika "+username+" je postavljena."); 
	}
}

