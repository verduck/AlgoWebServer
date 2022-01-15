# AlgoWeb

JWT RSA Key 생성

ssh-keygen -t rsa -m PEM
파일 이름 입력(rsa-key)

ssh-keygen -m PKCS8 -e

openssl pkcs8 -topk8 -inform pem -in rsa-key -outform pem -nocrypt -out rsa-key.pkcs8.private
