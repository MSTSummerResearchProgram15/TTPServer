package ttpserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang3.SerializationUtils;

import databasemanager.DatabaseManager;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class ProxyReEncryption {
	byte[] cipher1, cipher2;
	InputStream in;
	BufferedWriter bw;
	Element ownerPublicKey;
	Element c1, c2;
	public Element reencrypt(int userID, int count){
		DatabaseGetSet db = new DatabaseGetSet();
		FileReaderWriter bytes = new FileReaderWriter();
		ownerPublicKey.setFromBytes(db.getPublicKey(1)); //get the length of owner PK from database
		
		int ciphertextSize = ownerPublicKey.getLengthInBytes(); 
		cipher1 = new byte[ciphertextSize];
		cipher2 = new byte[ciphertextSize];
		PairingParameters curveParams = SerializationUtils.deserialize(db.getCurveParams(1));
		Pairing pairing = PairingFactory.getPairing(curveParams);
		Field g1 = pairing.getG1();
		Field gt = pairing.getGT();
		
		for(int i = 0; i < count; i++){
			in = new FileInputStream(new File("File" + i + ".txt"));
			bw = new BufferedWriter(new FileWriter(new File("ReEncryptedFile" + i + ".txt")));
			cipher1 = bytes.readFile(ciphertextSize, in);
			cipher2 = bytes.readFile(ciphertextSize, in);
			c1 = g1.newRandomElement();
			c2 = gt.newRandomElement();
			c1.setFromBytes(cipher1);
			c2.setFromBytes(cipher2);
			//Element reencrypt = pairing(c1, //reencrypt key here);
			Element ialpha = reencrypt.powZn(user1.getISK());
			//Need to transfer to client here
		}
	}
}
