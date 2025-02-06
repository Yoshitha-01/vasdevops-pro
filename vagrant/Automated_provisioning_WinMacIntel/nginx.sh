# adding repository and installing nginx		
apt update
apt install nginx -y
cat <<EOT > vasdevopsapp
upstream vasdevopsapp {

 server app01:8080;

}

server {

  listen 80;

location / {

  proxy_pass http://vasdevopsapp;

}

}

EOT

mv vasdevopsapp /etc/nginx/sites-available/vasdevopsapp
rm -rf /etc/nginx/sites-enabled/default
ln -s /etc/nginx/sites-available/vasdevopsapp /etc/nginx/sites-enabled/vasdevopsapp

#starting nginx service and firewall
systemctl start nginx
systemctl enable nginx
systemctl restart nginx
