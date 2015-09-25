package com.g.seed.web;

public abstract interface IEncryptor
{
	public abstract String exe(String string, EncryptType encryptType, EKeyType ekeyType);
}
