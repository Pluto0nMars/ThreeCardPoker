Client does not need logic of anything. I think it only needs the representation of data. However, for it to be serliazable, both files must have the exact same things

So it only needs:
* Card class with logic even tho it doesnt use it. Or can make DTO object
* shared.PokerInfo Serializable must be idential
* shared.ClientAction Class that contains requests like [BET, QUIT, FOLD etc...]
  

https://medium.com/@alxkm/the-complete-guide-to-data-transfer-objects-dtos-from-basics-to-enterprise-patterns-fcddd3a6bc9a