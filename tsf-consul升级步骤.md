## tsf-consul升级步骤

> 先滚动升级consul-config，再升级consul access
>
> 将升级的物料包上传到 /tmp下了

一、先升级tsf-consul-config，先升级非leader节点再去升级leader节点

> ##### 先查看leader节点master
>
> cd /opt/tsf/tsf-consul/tsf-consul-config/tsf-consul-config-v1.23.0/bin && sh cluster_health.sh

```shell
cd /opt/tsf/tsf-consul/tsf-consul-config/tsf-consul-config-v1.23.0/bin && sh stop.sh

cd ../../ && mv tsf-consul-config-v1.23.0/ ../tsf-consul-config-v1.23.0.back


unzip -d /opt/tsf/tsf-consul/tsf-consul-config/ /tmp/tsf-consul-config-v1.23.0.zip


cd /opt/tsf/tsf-consul/tsf-consul-config/tsf-consul-config-v1.23.0/bin && chmod +x *.sh

# 填写模板
# sh install.sh  -local_ip LOCAL_IP -mysql_password ENCLeftParenthesis加密后的mysql密码=RightParenthesis -mysql_vport vport -mysql_vip vip -ips consul-configIP列表 -idcs 所有节点对应的基础可用区 -roles 所有节点对应的节点状态（slave/master） 

68 -非leader
sh install.sh -local_ip 192.168.22.68 -mysql_password ENCLeftParenthesisspk4z9YIKWTnYSZMwl+FG0zVLN0bDlselg==RightParenthesis -mysql_vport 3306 -mysql_vip 192.168.22.87 -ips 192.168.22.69,192.168.22.68,192.168.22.70 -idcs tsfzone-96a79v5b -roles slave,slave,master
sh postProcess.sh 192.168.22.68 192.168.22.69,192.168.22.68,192.168.22.70


69 -非leader
sh install.sh -local_ip 192.168.22.69 -mysql_password ENCLeftParenthesisspk4z9YIKWTnYSZMwl+FG0zVLN0bDlselg==RightParenthesis -mysql_vport 3306 -mysql_vip 192.168.22.87 -ips 192.168.22.69,192.168.22.68,192.168.22.70 -idcs tsfzone-96a79v5b -roles slave,slave,master
sh postProcess.sh 192.168.22.69 192.168.22.69,192.168.22.68,192.168.22.70


70 -leader
sh install.sh -local_ip 192.168.22.70 -mysql_password ENCLeftParenthesisspk4z9YIKWTnYSZMwl+FG0zVLN0bDlselg==RightParenthesis -mysql_vport 3306 -mysql_vip 192.168.22.87 -ips 192.168.22.69,192.168.22.68,192.168.22.70 -idcs tsfzone-96a79v5b -roles slave,slave,master
sh postProcess.sh 192.168.22.70 192.168.22.69,192.168.22.68,192.168.22.70


## 执行postProcess.sh脚本。sh postProcess.sh local_IP IP_list
sh postProcess.sh 192.168.22.68 192.168.22.69,192.168.22.68,192.168.22.70


sh health.sh


# 测试读写，IP用刚升级完的这个local_ip
[root@i-x6yzayy5 bin]# curl  --request PUT --data 'hello consul'  http://192.168.22.68:8060/v1/kv/foo.test.tsf
true

[root@i-x6yzayy5 bin]# curl  http://192.168.22.68:8060/v1/kv/foo.test.tsf
[{"LockIndex":0,"Key":"foo.test.tsf","Flags":0,"Value":"aGVsbG8gY29uc3Vs","CreateIndex":1221266,"ModifyIndex":2719072}]
```

二、升级tsf-consul-access

```shell
cd /opt/tsf/tsf-consul/tsf-consul-access/tsf-consul-access-v1.23.5/bin/ && sh stop.sh 

cd ../../ && mv tsf-consul-access-v1.23.5 ../tsf-consul-access-v1.23.5.back

mkdir -p /opt/tsf/tsf-consul/tsf-consul-access/tsf-consul-access-v1.23.5

tar xfv /tmp/tsf-consul-access-v1.23.5.tar.gz -C /opt/tsf/tsf-consul/tsf-consul-access/tsf-consul-access-v1.23.5


cd /opt/tsf/tsf-consul/tsf-consul-access/tsf-consul-access-v1.23.5/bin && chmod +x *.sh

# 每个节点的local_ip修改下
./install.sh -local_ip 192.168.22.68 -mysql_password  ENCLeftParenthesisspk4z9YIKWTnYSZMwl+FG0zVLN0bDlselg==RightParenthesis -mysql_vport 3306 -mysql_vip 192.168.22.87

sh health.sh
```

