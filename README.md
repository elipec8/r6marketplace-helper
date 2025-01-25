# Rainbow 6 Siege Marketplace Trade Manager

This is app, which collects data about all items on marketplace and utilize it to user’s advantage, able to automatically create/update/cancel trade 
orders on user’s account.  
Requires from end user only his Ubisoft Account’s credentials, specification of items to be managed and boundaries for manager, which can be 
 configured for each item separately.  
&nbsp;  
[Marketplace](https://www.ubisoft.com/en-us/game/rainbow-six/siege/marketplace?route=home)


## To start:  
  1. Provide required auth_constants.env file by its sample  
    &nbsp;  
 2. Either provide **fetching_accounts_credentials.json** in **fetching_users_reauthorizer** module OR  
    Configure its docker-compose service to get the file from another source  
     &nbsp;
 3. Run the app with **docker-compose up** in root directory


## To use:

For using **Fast Sell Trade Manager**, after linking your Ubisoft Account via Telegram Bot,
configure environment variables in **fast_sell_trade_manager** module's docker-compose service to select proper user.  


For using **Trades Manager**, link your Ubisoft Account and create Trade Manager via Telegram Bot.  
Trade Managers can use either Items' Filters or single Item ID. User can have any amount of Trade Managers.  
  * For Managers by Item ID, you can provide boundary prices to be sold/bought and multiplier for trade priority, to highlight managed item among 
   items from other managers.  
   * For Managers by Item Filters, instead of boundary prices, you can provide minimum difference between trade price and item's month median price.
      Can use few Item Filters at once.  Item Filters are also created by User.



## Environmental authorization variables description:  

>**UBI_PLATFORM** - platform of your Ubisoft Account's, typically UPLAY  
**UBI_EMAIL** - your Ubisoft Account's email  
**UBI_PASSWORD** - your Ubisoft Account's password  
&nbsp;  
Used in  **Main User Reauthorizer**.  
Accounts itself is used in **Configs Fetcher**, **Item Day Sales Ubi Stats Fetcher**, **Item Stats Fetcher**.   
It's better to not use it in **fetching_accounts_credentials.json**, to prevent too many requests error during stats fetching.  
&nbsp;  
[Create Ubisoft Account](https://account.ubisoft.com/en-US/login)


>**TELEGRAM_BOT_TOKEN** - your Telegram bot token, receive from @BotFather  
**TELEGRAM_BOT_USERNAME** - your Telegram bot username, receive from @BotFather  
&nbsp;  
[@BotFather](https://t.me/BotFather)


>**PASSWORD_ENCRYPTION_KEY** - AES password encryption key for storing Ubisoft Accounts' passwords in database  
&nbsp;  
[AES encryption key generator - get Encryption Key 256](https://acte.ltd/utils/randomkeygen)


>**POSTGRES_USER** - postgres container root user   
**POSTGRES_PASSWORD** - postgres container root password  
&nbsp;  
**PGADMIN_DEFAULT_EMAIL** - pgadmin container root user email  
**PGADMIN_DEFAULT_PASSWORD** - pgadmin default root user password  


## Modules description:


* **Configs Fetcher** - fetches all available Item Tags and Types, marketplace's limitations of Trade slots, day limits and expire timeout.  
Uses Main Ubisoft Account's Authorization Token and Headers, received from Redis service.


* **Fast Sell Trade Manager** - manages sell trades only for 1 selected user from database.  
Selects managed account by User's ID and linked Ubisoft Account's email, provided in configuration.  
Uses this account's Authorization Token and Headers, received from DB.  
Also uses Fetching Users from DB to fetch the data more often, avoiding too many requests error.


* **Fetching Users Reauthorizer** - reauthorizes Fetching Users, which are used in **Fast Sell Trade Manager** and
saves their Authorization Tokens and Headers to DB.


* **Item Day Sales Ubi Stats Fetcher** - fetches and saves to DB all items last month sales stats, provided by Ubisoft (only min/max/avg prices and 
sales count for item per each day).  
Uses Main Ubisoft Account's Authorization Token and Headers, received from Redis service.


* **Item Stats Fetcher** - fetches and saves to DB all Items' current stats, including their last sales, as more accurate sale stats, comparing to 
Day Sales Stats, provided by Ubisoft. May notify all users via Notifications Service, if items' amount was increased (which means that Marketplace was updated).  
Uses Main Ubisoft Account's Authorization Token and Headers, received from Redis service.


* **Item Trade Stats Calculator** - recalculates Items' sale history stats in DB, such as min/max/avg/median prices, approximate price to buy in 
chosen time or to time to sell by chosen price, trade priorities, bases on Item's sale stats. Saves recalculated stats to DB.


* **Main User Reauthorizer** - reauthorizes Main User, which is used in **Configs Fetcher**, **Item Day Sales Ubi Stats Fetcher**
and **Item Stats Fetcher** to fetch data from Ubisoft Server. Saves its Authorization Token and Headers to Redis service.   


* **Notifications Service** - sends notifications to User via Telegram Bot, consuming them from Kafka service.  


* **Telegram Bot Updates Consumer** - the main and only user interface. Provided via Telegram Bot.  
Provides methods to:  
Fetch Items by Item Filters  
Operate Trade Managers  
Operate Item Filters  
Link Managed Ubisoft Account  
Configure Notifications  


* **Trades Manager** - manages trades for all Users in DB, who have linked Ubisoft Account, have trade managers and enabled trade management setting.
Notifies Users via Notifications Service about performed operations.  


* **Ubi Users Stats Fetcher** - fetches stats for linked Ubisoft Accounts and notify their owners via Notifications Service about changes.


* **Ubi Users Stats Reauthorizer** - reauthorizes linked Ubisoft Accounts and saves their Authorization Tokens and Headers to DB.  
Notifies Users via Notifications Service if theirs Ubisoft Account reauthorization failed.


* **Utils** - contains shared resources:  
utils-dependencies - parent pom.xml for all modules.  
utils-library - shared DTOs, Enums, Exceptions, Services, etc.  
utils-library-graphQl - everything related to GraphQL.  
utils-library-kafka - classes, related to Kafka producers.  
utils-library-redis - Redis service.  
utils-postgres-schema - JPA entities and id classes.


