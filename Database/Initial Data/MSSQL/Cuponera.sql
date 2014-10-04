
USE Cuponera
GO
 IF NOT EXISTS(SELECT * FROM sys.schemas WHERE [name] = N'dbo')      
     EXEC (N'CREATE SCHEMA dbo')                                   
 GO                                                               

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'campaign'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'campaign'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[campaign]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[campaign]
(
   [CampaignId] int IDENTITY(4, 1)  NOT NULL,
   [Step] nchar(1)  NOT NULL,
   [Resume] nchar(1) DEFAULT NULL  NULL,
   [Name] nvarchar(200) DEFAULT NULL  NULL,
   [Message] nvarchar(255)  NOT NULL,
   [MessageAction] nvarchar(255) DEFAULT NULL  NULL,
   [MessageActionLabel] nvarchar(50) DEFAULT NULL  NULL,
   [OriginalStartDate] datetime2(0)  NOT NULL,
   [StartDate] datetime2(0)  NOT NULL,
   [EndDate] datetime2(0) DEFAULT NULL  NULL,
   [ContinueNextDay] int  NOT NULL,
   [Total] int DEFAULT NULL  NULL,
   [Success] int DEFAULT NULL  NULL,
   [Fail] int DEFAULT NULL  NULL,
   [LastUpdate] datetime2(0) DEFAULT NULL  NULL,
   [LastOs] nchar(1) DEFAULT NULL  NULL,
   [LockId] nvarchar(36) DEFAULT NULL  NULL,
   [LockDate] datetime2(0) DEFAULT NULL  NULL,
   [FirstExecution] datetime2(0) DEFAULT NULL  NULL,
   [Discarded] int DEFAULT NULL  NULL,
   [CreationDate] datetime DEFAULT getdate()  NULL,
   [TestProfileId] int DEFAULT NULL  NULL,
   [UploadToken] nvarchar(36) DEFAULT NULL  NULL,
   [ProfileGroupId] int DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.campaign',
        N'SCHEMA', N'dbo',
        N'TABLE', N'campaign'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'coupons'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'coupons'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[coupons]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[coupons]
(
   [IdCoupon] int IDENTITY(8, 1)  NOT NULL,
   [Title] nvarchar(100) DEFAULT NULL  NULL,
   [PromoCode] nvarchar(50) DEFAULT NULL  NULL,
   [TargetURL] nvarchar(255) DEFAULT NULL  NULL,
   [Active] int DEFAULT NULL  NULL,
   [StartDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ExpirationDatetime] datetime2(0) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [ItemOrder] int DEFAULT 0  NULL,
   [IdProfileGroup] int DEFAULT NULL  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.coupons',
        N'SCHEMA', N'dbo',
        N'TABLE', N'coupons'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'deviceos'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'deviceos'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[deviceos]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[deviceos]
(
   [IdDeviceOs] int IDENTITY(3, 1)  NOT NULL,
   [Code] nvarchar(20)  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.deviceos',
        N'SCHEMA', N'dbo',
        N'TABLE', N'deviceos'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'devicetypes'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'devicetypes'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[devicetypes]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[devicetypes]
(
   [IdDeviceType] int IDENTITY(3, 1)  NOT NULL,
   [Code] nvarchar(20)  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.devicetypes',
        N'SCHEMA', N'dbo',
        N'TABLE', N'devicetypes'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'enabledsections'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'enabledsections'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[enabledsections]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[enabledsections]
(
   [IdEnabledSection] int IDENTITY(37, 1)  NOT NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0055: Data type was converted to VARCHAR according to character set mapping for latin1 character set
   */

   [Section] varchar(45) DEFAULT NULL  NULL,
   [IdDeviceOs] int DEFAULT NULL  NULL,
   [IsEnabled] int DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.enabledsections',
        N'SCHEMA', N'dbo',
        N'TABLE', N'enabledsections'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'failednotification'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'failednotification'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[failednotification]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[failednotification]
(
   [CampaignId] int  NOT NULL,
   [ProfileId] int  NOT NULL,
   [Xid] nvarchar(30)  NOT NULL,
   [Os] nchar(1)  NOT NULL,
   [Error] nvarchar(50)  NOT NULL,
   [ErrorDetail] nvarchar(255) DEFAULT NULL  NULL,
   [ProcessedDate] datetime DEFAULT getdate()  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.failednotification',
        N'SCHEMA', N'dbo',
        N'TABLE', N'failednotification'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'homeoffers'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'homeoffers'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[homeoffers]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[homeoffers]
(
   [IdHomeOffer] int IDENTITY(8, 1)  NOT NULL,
   [TargetURL] nvarchar(255) DEFAULT NULL  NULL,
   [Active] int DEFAULT NULL  NULL,
   [StartDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ExpirationDatetime] datetime2(0) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [ItemOrder] int DEFAULT 0  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.homeoffers',
        N'SCHEMA', N'dbo',
        N'TABLE', N'homeoffers'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'imagesizes'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'imagesizes'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[imagesizes]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[imagesizes]
(
   [IdImageSize] int IDENTITY(4, 1)  NOT NULL,
   [Name] nvarchar(45) DEFAULT NULL  NULL,
   [IdDeviceOs] int  NOT NULL,
   [Width] int DEFAULT NULL  NULL,
   [Height] int DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.imagesizes',
        N'SCHEMA', N'dbo',
        N'TABLE', N'imagesizes'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'lookbookattributecategories'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'lookbookattributecategories'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[lookbookattributecategories]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[lookbookattributecategories]
(
   [IdLookBookAttributeCategory] int IDENTITY(5, 1)  NOT NULL,
   [Code] nvarchar(40)  NOT NULL,
   [Name] nvarchar(100) DEFAULT NULL  NULL,
   [ItemOrder] int DEFAULT NULL  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.lookbookattributecategories',
        N'SCHEMA', N'dbo',
        N'TABLE', N'lookbookattributecategories'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'lookbookattributes'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'lookbookattributes'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[lookbookattributes]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[lookbookattributes]
(
   [IdLookBookAttribute] int IDENTITY(4040, 1)  NOT NULL,
   [IdLookBookAttributeCategory] int  NOT NULL,
   [Name] nvarchar(255) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [ItemOrder] int DEFAULT 0  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.lookbookattributes',
        N'SCHEMA', N'dbo',
        N'TABLE', N'lookbookattributes'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'lookbookitemattributes'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'lookbookitemattributes'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[lookbookitemattributes]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[lookbookitemattributes]
(
   [idLookBookItemAttribute] int IDENTITY(291, 1)  NOT NULL,
   [IdLookBookItem] int  NOT NULL,
   [IdLookBookAttribute] int  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.lookbookitemattributes',
        N'SCHEMA', N'dbo',
        N'TABLE', N'lookbookitemattributes'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'lookbookitems'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'lookbookitems'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[lookbookitems]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[lookbookitems]
(
   [IdLookBookItem] int IDENTITY(699, 1)  NOT NULL,
   [Name] nvarchar(255) DEFAULT NULL  NULL,
   [Description] nvarchar(800) DEFAULT NULL  NULL,
   [LotId] nvarchar(100) DEFAULT NULL  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.lookbookitems',
        N'SCHEMA', N'dbo',
        N'TABLE', N'lookbookitems'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'modules'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'modules'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[modules]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[modules]
(
   [idModule] int IDENTITY(14, 1)  NOT NULL,
   [Name] nvarchar(45)  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.modules',
        N'SCHEMA', N'dbo',
        N'TABLE', N'modules'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'pendingnotification'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'pendingnotification'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[pendingnotification]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[pendingnotification]
(
   [CampaignId] int  NOT NULL,
   [ProfileId] int  NOT NULL,
   [Xid] nvarchar(30)  NOT NULL,
   [Os] nchar(1)  NOT NULL,
   [LockId] nvarchar(36) DEFAULT NULL  NULL,
   [LockDate] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.pendingnotification',
        N'SCHEMA', N'dbo',
        N'TABLE', N'pendingnotification'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'permissions'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'permissions'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[permissions]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[permissions]
(
   [idPermission] int IDENTITY(14, 1)  NOT NULL,
   [IdRole] int  NOT NULL,
   [IdModule] int  NOT NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [Read] smallint DEFAULT 0  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [Create] smallint DEFAULT 0  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [Update] smallint DEFAULT 0  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [Delete] smallint DEFAULT 0  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.permissions',
        N'SCHEMA', N'dbo',
        N'TABLE', N'permissions'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'prehomeimages'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'prehomeimages'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[prehomeimages]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[prehomeimages]
(
   [IdPreHomeImage] int IDENTITY(17, 1)  NOT NULL,
   [Active] int DEFAULT NULL  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.prehomeimages',
        N'SCHEMA', N'dbo',
        N'TABLE', N'prehomeimages'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupdeviceos'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'profilegroupdeviceos'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[profilegroupdeviceos]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[profilegroupdeviceos]
(
   [IdProfileGroupDeviceOs] int IDENTITY(91, 1)  NOT NULL,
   [IdProfileGroup] int  NOT NULL,
   [IdDeviceOs] int  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.profilegroupdeviceos',
        N'SCHEMA', N'dbo',
        N'TABLE', N'profilegroupdeviceos'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupmembers'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'profilegroupmembers'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[profilegroupmembers]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[profilegroupmembers]
(
   [IdProfileGroupMember] int IDENTITY(14397, 1)  NOT NULL,
   [IdProfileGroup] int  NOT NULL,
   [IdProfile] int  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.profilegroupmembers',
        N'SCHEMA', N'dbo',
        N'TABLE', N'profilegroupmembers'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupmemberstemporary'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'profilegroupmemberstemporary'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[profilegroupmemberstemporary]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[profilegroupmemberstemporary]
(
   [IdProfile] int  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.profilegroupmemberstemporary',
        N'SCHEMA', N'dbo',
        N'TABLE', N'profilegroupmemberstemporary'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroups'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'profilegroups'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[profilegroups]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[profilegroups]
(
   [IdProfileGroup] int IDENTITY(17, 1)  NOT NULL,
   [Name] nvarchar(45) DEFAULT NULL  NULL,
   [IsFixed] int DEFAULT NULL  NULL,
   [AppVersion] nvarchar(45) DEFAULT NULL  NULL,
   [Geolocation] int DEFAULT NULL  NULL,
   [PushNotification] int DEFAULT NULL  NULL,
   [SMSNotification] int DEFAULT NULL  NULL,
   [EmailNotification] int DEFAULT NULL  NULL,
   [PostalNotification] int DEFAULT NULL  NULL,
   [MinAge] int DEFAULT NULL  NULL,
   [MembersCount] int DEFAULT NULL  NULL,
   [MembersComputingDatetime] datetime2(0) DEFAULT NULL  NULL,
   [LockId] nchar(40) DEFAULT NULL  NULL,
   [LockDate] datetime2(0) DEFAULT NULL  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.profilegroups',
        N'SCHEMA', N'dbo',
        N'TABLE', N'profilegroups'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupstates'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'profilegroupstates'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[profilegroupstates]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[profilegroupstates]
(
   [ProfileGroupStates] int IDENTITY(82, 1)  NOT NULL,
   [IdProfileGroup] int  NOT NULL,
   [IdState] nchar(2)  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.profilegroupstates',
        N'SCHEMA', N'dbo',
        N'TABLE', N'profilegroupstates'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupzipcodes'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'profilegroupzipcodes'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[profilegroupzipcodes]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[profilegroupzipcodes]
(
   [IdProfileGroupZipcode] int IDENTITY(39, 1)  NOT NULL,
   [IdProfileGroup] int  NOT NULL,
   [Zipcode] nvarchar(5)  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.profilegroupzipcodes',
        N'SCHEMA', N'dbo',
        N'TABLE', N'profilegroupzipcodes'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profiles'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'profiles'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[profiles]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[profiles]
(
   [IdProfile] int IDENTITY(181637, 1)  NOT NULL,
   [DeviceId] nchar(40)  NOT NULL,
   [IdDeviceType] int  NOT NULL,
   [IdDeviceOs] int  NOT NULL,
   [AppVersion] nvarchar(40)  NOT NULL,
   [ResolutionWidth] int DEFAULT NULL  NULL,
   [ResolutionHeight] int DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [CompletedRegistration] int DEFAULT 0  NULL,
   [Latitude] float(53) DEFAULT NULL  NULL,
   [Longitude] float(53) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [Geolocation] int DEFAULT 0  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [PushNotification] int DEFAULT 0  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [SMSNotification] int DEFAULT 0  NULL,
   [Xid] nvarchar(40) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [EmailNotification] int DEFAULT 0  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [PostalNotification] int DEFAULT 0  NULL,
   [FirstName] nvarchar(255) DEFAULT NULL  NULL,
   [LastName] nvarchar(255) DEFAULT NULL  NULL,
   [Email] nvarchar(255) DEFAULT NULL  NULL,
   [BirthDate] date DEFAULT NULL  NULL,
   [Gender] nvarchar(45) DEFAULT NULL  NULL,
   [Address_1] nvarchar(255) DEFAULT NULL  NULL,
   [Address_2] nvarchar(255) DEFAULT NULL  NULL,
   [City] nvarchar(255) DEFAULT NULL  NULL,
   [IdState] nchar(2) DEFAULT NULL  NULL,
   [ZipCode] nvarchar(5) DEFAULT NULL  NULL,
   [PhoneNumber] nvarchar(20) DEFAULT NULL  NULL,
   [LastAppUseDatetime] datetime2(0) DEFAULT NULL  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.profiles',
        N'SCHEMA', N'dbo',
        N'TABLE', N'profiles'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'pushnotificationsratio'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'pushnotificationsratio'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[pushnotificationsratio]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[pushnotificationsratio]
(
   [Ratio] int  NOT NULL,
   [UpdateDateTime] datetime2(0)  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.pushnotificationsratio',
        N'SCHEMA', N'dbo',
        N'TABLE', N'pushnotificationsratio'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'roles'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'roles'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[roles]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[roles]
(
   [idRole] int IDENTITY(2, 1)  NOT NULL,
   [Name] nvarchar(45)  NOT NULL,
   [Rolescol] nvarchar(45) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.roles',
        N'SCHEMA', N'dbo',
        N'TABLE', N'roles'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'socialfeedaccesslog'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'socialfeedaccesslog'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[socialfeedaccesslog]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[socialfeedaccesslog]
(
   [IdSocialFeedAccessLog] int IDENTITY(3, 1)  NOT NULL,
   [Source] nvarchar(255) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.socialfeedaccesslog',
        N'SCHEMA', N'dbo',
        N'TABLE', N'socialfeedaccesslog'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'socialupdates'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'socialupdates'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[socialupdates]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[socialupdates]
(
   [IdSocialUpdate] int IDENTITY(2325, 1)  NOT NULL,
   [IdSourceItem] nchar(32)  NOT NULL,
   [Name] nvarchar(800) DEFAULT NULL  NULL,
   [Text] nvarchar(800) DEFAULT NULL  NULL,
   [ImageURL] nvarchar(255) DEFAULT NULL  NULL,
   [TargetURL] nvarchar(255) DEFAULT NULL  NULL,
   [Source] nchar(2)  NOT NULL,
   [SocialItemDatetime] datetime2(0) DEFAULT NULL  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.socialupdates',
        N'SCHEMA', N'dbo',
        N'TABLE', N'socialupdates'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'sqlscriptlog'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'sqlscriptlog'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[sqlscriptlog]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[sqlscriptlog]
(

   /*
   *   SSMA informational messages:
   *   M2SS0055: Data type was converted to VARCHAR according to character set mapping for latin1 character set
   */

   [Id] varchar(36)  NOT NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0055: Data type was converted to VARCHAR according to character set mapping for latin1 character set
   */

   [FileName] varchar(255) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0055: Data type was converted to VARCHAR according to character set mapping for latin1 character set
   */

   [Iteration] varchar(10) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0055: Data type was converted to VARCHAR according to character set mapping for latin1 character set
   */

   [UserName] varchar(50) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0055: Data type was converted to VARCHAR according to character set mapping for latin1 character set
   */

   [Comment] varchar(1024) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0055: Data type was converted to VARCHAR according to character set mapping for latin1 character set
   */

   [ErrorMessage] varchar(1024) DEFAULT NULL  NULL,
   [ExecutionDateTime] datetime2(0)  NOT NULL,
   [ExecutionStatus] int  NOT NULL,
   [Forced] smallint  NOT NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0055: Data type was converted to VARCHAR according to character set mapping for latin1 character set
   */

   [HashCode] varchar(50) DEFAULT NULL  NULL,
   [Timestamp] binary(8) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.sqlscriptlog',
        N'SCHEMA', N'dbo',
        N'TABLE', N'sqlscriptlog'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'states'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'states'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[states]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[states]
(
   [IdState] nchar(2)  NOT NULL,
   [Name] nvarchar(255) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.states',
        N'SCHEMA', N'dbo',
        N'TABLE', N'states'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'stores'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'stores'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[stores]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[stores]
(
   [IdStore] int  NOT NULL,
   [Name] nvarchar(255) DEFAULT NULL  NULL,
   [Address] nvarchar(255) DEFAULT NULL  NULL,
   [ContactNumber] nvarchar(40) DEFAULT NULL  NULL,
   [Latitude] float(53) DEFAULT NULL  NULL,
   [Longitude] float(53) DEFAULT NULL  NULL,
   [ZipCode] nvarchar(10) DEFAULT NULL  NULL,
   [City] nvarchar(100) DEFAULT NULL  NULL,
   [IdState] nchar(2)  NOT NULL,
   [StoreHours] nvarchar(max)  NULL,
   [GeolocationProcessingDatetime] datetime2(0) DEFAULT NULL  NULL,

   /*
   *   SSMA informational messages:
   *   M2SS0052: string literal was converted to NUMERIC literal
   */

   [GeolocationTries] int DEFAULT 0  NOT NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.stores',
        N'SCHEMA', N'dbo',
        N'TABLE', N'stores'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'succeednotification'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'succeednotification'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[succeednotification]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[succeednotification]
(
   [CampaignId] int  NOT NULL,
   [ProfileId] int  NOT NULL,
   [Xid] nvarchar(30)  NOT NULL,
   [Os] nchar(1)  NOT NULL,
   [JobId] nvarchar(255) DEFAULT NULL  NULL,
   [ProcessedDate] datetime DEFAULT getdate()  NOT NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.succeednotification',
        N'SCHEMA', N'dbo',
        N'TABLE', N'succeednotification'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'users'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'users'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[users]
END 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE 
[dbo].[users]
(
   [IdUser] int IDENTITY(3, 1)  NOT NULL,
   [Email] nvarchar(45)  NOT NULL,
   [Password] nvarchar(255)  NOT NULL,
   [Name] nvarchar(255) DEFAULT NULL  NULL,
   [IdRole] int DEFAULT NULL  NULL,
   [CreationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [ModificationDatetime] datetime2(0) DEFAULT NULL  NULL,
   [DeletionDatetime] datetime2(0) DEFAULT NULL  NULL
)
GO
BEGIN TRY
    EXEC sp_addextendedproperty
        N'MS_SSMA_SOURCE', N'cuponera.users',
        N'SCHEMA', N'dbo',
        N'TABLE', N'users'
END TRY
BEGIN CATCH
    IF (@@TRANCOUNT > 0) ROLLBACK
    PRINT ERROR_MESSAGE()
END CATCH
GO

USE Cuponera
GO
IF  EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'xtifytimelog'  AND sc.name=N'dbo'  AND type in (N'U'))
BEGIN

  DECLARE @drop_statement nvarchar(500)

  DECLARE drop_cursor CURSOR FOR
      SELECT 'alter table '+quotename(schema_name(ob.schema_id))+
      '.'+quotename(object_name(ob.object_id))+ ' drop constraint ' + quotename(fk.name) 
      FROM sys.objects ob INNER JOIN sys.foreign_keys fk ON fk.parent_object_id = ob.object_id
      WHERE fk.referenced_object_id = 
          (
             SELECT so.object_id 
             FROM sys.objects so JOIN sys.schemas sc
             ON so.schema_id = sc.schema_id
             WHERE so.name = N'xtifytimelog'  AND sc.name=N'dbo'  AND type in (N'U')
           )

  OPEN drop_cursor

  FETCH NEXT FROM drop_cursor
  INTO @drop_statement

  WHILE @@FETCH_STATUS = 0
  BEGIN
     EXEC (@drop_statement)

     FETCH NEXT FROM drop_cursor
     INTO @drop_statement
  END

  CLOSE drop_cursor
  DEALLOCATE drop_cursor

  DROP TABLE [dbo].[xtifytimelog]
END 
GO



USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_campaign_CampaignId'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[campaign] DROP CONSTRAINT [PK_campaign_CampaignId]
 GO



ALTER TABLE [dbo].[campaign]
 ADD CONSTRAINT [PK_campaign_CampaignId]
 PRIMARY KEY 
   CLUSTERED ([CampaignId] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_coupons_IdCoupon'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[coupons] DROP CONSTRAINT [PK_coupons_IdCoupon]
 GO



ALTER TABLE [dbo].[coupons]
 ADD CONSTRAINT [PK_coupons_IdCoupon]
 PRIMARY KEY 
   CLUSTERED ([IdCoupon] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_deviceos_IdDeviceOs'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[deviceos] DROP CONSTRAINT [PK_deviceos_IdDeviceOs]
 GO



ALTER TABLE [dbo].[deviceos]
 ADD CONSTRAINT [PK_deviceos_IdDeviceOs]
 PRIMARY KEY 
   CLUSTERED ([IdDeviceOs] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_devicetypes_IdDeviceType'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[devicetypes] DROP CONSTRAINT [PK_devicetypes_IdDeviceType]
 GO



ALTER TABLE [dbo].[devicetypes]
 ADD CONSTRAINT [PK_devicetypes_IdDeviceType]
 PRIMARY KEY 
   CLUSTERED ([IdDeviceType] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_enabledsections_IdEnabledSection'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[enabledsections] DROP CONSTRAINT [PK_enabledsections_IdEnabledSection]
 GO



ALTER TABLE [dbo].[enabledsections]
 ADD CONSTRAINT [PK_enabledsections_IdEnabledSection]
 PRIMARY KEY 
   CLUSTERED ([IdEnabledSection] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_failednotification_CampaignId'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[failednotification] DROP CONSTRAINT [PK_failednotification_CampaignId]
 GO



ALTER TABLE [dbo].[failednotification]
 ADD CONSTRAINT [PK_failednotification_CampaignId]
 PRIMARY KEY 
   CLUSTERED ([CampaignId] ASC, [ProfileId] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_homeoffers_IdHomeOffer'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[homeoffers] DROP CONSTRAINT [PK_homeoffers_IdHomeOffer]
 GO



ALTER TABLE [dbo].[homeoffers]
 ADD CONSTRAINT [PK_homeoffers_IdHomeOffer]
 PRIMARY KEY 
   CLUSTERED ([IdHomeOffer] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_imagesizes_IdImageSize'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[imagesizes] DROP CONSTRAINT [PK_imagesizes_IdImageSize]
 GO



ALTER TABLE [dbo].[imagesizes]
 ADD CONSTRAINT [PK_imagesizes_IdImageSize]
 PRIMARY KEY 
   CLUSTERED ([IdImageSize] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_lookbookattributecategories_IdLookBookAttributeCategory'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[lookbookattributecategories] DROP CONSTRAINT [PK_lookbookattributecategories_IdLookBookAttributeCategory]
 GO



ALTER TABLE [dbo].[lookbookattributecategories]
 ADD CONSTRAINT [PK_lookbookattributecategories_IdLookBookAttributeCategory]
 PRIMARY KEY 
   CLUSTERED ([IdLookBookAttributeCategory] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_lookbookattributes_IdLookBookAttribute'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[lookbookattributes] DROP CONSTRAINT [PK_lookbookattributes_IdLookBookAttribute]
 GO



ALTER TABLE [dbo].[lookbookattributes]
 ADD CONSTRAINT [PK_lookbookattributes_IdLookBookAttribute]
 PRIMARY KEY 
   CLUSTERED ([IdLookBookAttribute] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_lookbookitemattributes_idLookBookItemAttribute'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[lookbookitemattributes] DROP CONSTRAINT [PK_lookbookitemattributes_idLookBookItemAttribute]
 GO



ALTER TABLE [dbo].[lookbookitemattributes]
 ADD CONSTRAINT [PK_lookbookitemattributes_idLookBookItemAttribute]
 PRIMARY KEY 
   CLUSTERED ([idLookBookItemAttribute] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_lookbookitems_IdLookBookItem'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[lookbookitems] DROP CONSTRAINT [PK_lookbookitems_IdLookBookItem]
 GO



ALTER TABLE [dbo].[lookbookitems]
 ADD CONSTRAINT [PK_lookbookitems_IdLookBookItem]
 PRIMARY KEY 
   CLUSTERED ([IdLookBookItem] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_modules_idModule'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[modules] DROP CONSTRAINT [PK_modules_idModule]
 GO



ALTER TABLE [dbo].[modules]
 ADD CONSTRAINT [PK_modules_idModule]
 PRIMARY KEY 
   CLUSTERED ([idModule] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_pendingnotification_CampaignId'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[pendingnotification] DROP CONSTRAINT [PK_pendingnotification_CampaignId]
 GO



ALTER TABLE [dbo].[pendingnotification]
 ADD CONSTRAINT [PK_pendingnotification_CampaignId]
 PRIMARY KEY 
   CLUSTERED ([CampaignId] ASC, [ProfileId] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_permissions_idPermission'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[permissions] DROP CONSTRAINT [PK_permissions_idPermission]
 GO



ALTER TABLE [dbo].[permissions]
 ADD CONSTRAINT [PK_permissions_idPermission]
 PRIMARY KEY 
   CLUSTERED ([idPermission] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_prehomeimages_IdPreHomeImage'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[prehomeimages] DROP CONSTRAINT [PK_prehomeimages_IdPreHomeImage]
 GO



ALTER TABLE [dbo].[prehomeimages]
 ADD CONSTRAINT [PK_prehomeimages_IdPreHomeImage]
 PRIMARY KEY 
   CLUSTERED ([IdPreHomeImage] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_profilegroupdeviceos_IdProfileGroupDeviceOs'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[profilegroupdeviceos] DROP CONSTRAINT [PK_profilegroupdeviceos_IdProfileGroupDeviceOs]
 GO



ALTER TABLE [dbo].[profilegroupdeviceos]
 ADD CONSTRAINT [PK_profilegroupdeviceos_IdProfileGroupDeviceOs]
 PRIMARY KEY 
   CLUSTERED ([IdProfileGroupDeviceOs] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_profilegroupmembers_IdProfileGroupMember'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[profilegroupmembers] DROP CONSTRAINT [PK_profilegroupmembers_IdProfileGroupMember]
 GO



ALTER TABLE [dbo].[profilegroupmembers]
 ADD CONSTRAINT [PK_profilegroupmembers_IdProfileGroupMember]
 PRIMARY KEY 
   CLUSTERED ([IdProfileGroupMember] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_profilegroupmemberstemporary_IdProfile'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[profilegroupmemberstemporary] DROP CONSTRAINT [PK_profilegroupmemberstemporary_IdProfile]
 GO



ALTER TABLE [dbo].[profilegroupmemberstemporary]
 ADD CONSTRAINT [PK_profilegroupmemberstemporary_IdProfile]
 PRIMARY KEY 
   CLUSTERED ([IdProfile] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_profilegroups_IdProfileGroup'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[profilegroups] DROP CONSTRAINT [PK_profilegroups_IdProfileGroup]
 GO



ALTER TABLE [dbo].[profilegroups]
 ADD CONSTRAINT [PK_profilegroups_IdProfileGroup]
 PRIMARY KEY 
   CLUSTERED ([IdProfileGroup] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_profilegroupstates_ProfileGroupStates'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[profilegroupstates] DROP CONSTRAINT [PK_profilegroupstates_ProfileGroupStates]
 GO



ALTER TABLE [dbo].[profilegroupstates]
 ADD CONSTRAINT [PK_profilegroupstates_ProfileGroupStates]
 PRIMARY KEY 
   CLUSTERED ([ProfileGroupStates] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_profilegroupzipcodes_IdProfileGroupZipcode'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[profilegroupzipcodes] DROP CONSTRAINT [PK_profilegroupzipcodes_IdProfileGroupZipcode]
 GO



ALTER TABLE [dbo].[profilegroupzipcodes]
 ADD CONSTRAINT [PK_profilegroupzipcodes_IdProfileGroupZipcode]
 PRIMARY KEY 
   CLUSTERED ([IdProfileGroupZipcode] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_profiles_IdProfile'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[profiles] DROP CONSTRAINT [PK_profiles_IdProfile]
 GO



ALTER TABLE [dbo].[profiles]
 ADD CONSTRAINT [PK_profiles_IdProfile]
 PRIMARY KEY 
   CLUSTERED ([IdProfile] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_pushnotificationsratio_UpdateDateTime'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[pushnotificationsratio] DROP CONSTRAINT [PK_pushnotificationsratio_UpdateDateTime]
 GO



ALTER TABLE [dbo].[pushnotificationsratio]
 ADD CONSTRAINT [PK_pushnotificationsratio_UpdateDateTime]
 PRIMARY KEY 
   CLUSTERED ([UpdateDateTime] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_roles_idRole'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[roles] DROP CONSTRAINT [PK_roles_idRole]
 GO



ALTER TABLE [dbo].[roles]
 ADD CONSTRAINT [PK_roles_idRole]
 PRIMARY KEY 
   CLUSTERED ([idRole] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_socialfeedaccesslog_IdSocialFeedAccessLog'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[socialfeedaccesslog] DROP CONSTRAINT [PK_socialfeedaccesslog_IdSocialFeedAccessLog]
 GO



ALTER TABLE [dbo].[socialfeedaccesslog]
 ADD CONSTRAINT [PK_socialfeedaccesslog_IdSocialFeedAccessLog]
 PRIMARY KEY 
   CLUSTERED ([IdSocialFeedAccessLog] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_socialupdates_IdSocialUpdate'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[socialupdates] DROP CONSTRAINT [PK_socialupdates_IdSocialUpdate]
 GO



ALTER TABLE [dbo].[socialupdates]
 ADD CONSTRAINT [PK_socialupdates_IdSocialUpdate]
 PRIMARY KEY 
   CLUSTERED ([IdSocialUpdate] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_sqlscriptlog_Id'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[sqlscriptlog] DROP CONSTRAINT [PK_sqlscriptlog_Id]
 GO



ALTER TABLE [dbo].[sqlscriptlog]
 ADD CONSTRAINT [PK_sqlscriptlog_Id]
 PRIMARY KEY 
   CLUSTERED ([Id] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_states_IdState'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[states] DROP CONSTRAINT [PK_states_IdState]
 GO



ALTER TABLE [dbo].[states]
 ADD CONSTRAINT [PK_states_IdState]
 PRIMARY KEY 
   CLUSTERED ([IdState] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_stores_IdStore'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[stores] DROP CONSTRAINT [PK_stores_IdStore]
 GO



ALTER TABLE [dbo].[stores]
 ADD CONSTRAINT [PK_stores_IdStore]
 PRIMARY KEY 
   CLUSTERED ([IdStore] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_succeednotification_CampaignId'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[succeednotification] DROP CONSTRAINT [PK_succeednotification_CampaignId]
 GO



ALTER TABLE [dbo].[succeednotification]
 ADD CONSTRAINT [PK_succeednotification_CampaignId]
 PRIMARY KEY 
   CLUSTERED ([CampaignId] ASC, [ProfileId] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'PK_users_IdUser'  AND sc.name=N'dbo'  AND type in (N'PK'))
ALTER TABLE [dbo].[users] DROP CONSTRAINT [PK_users_IdUser]
 GO



ALTER TABLE [dbo].[users]
 ADD CONSTRAINT [PK_users_IdUser]
 PRIMARY KEY 
   CLUSTERED ([IdUser] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'deviceos$Code_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[deviceos] DROP CONSTRAINT [deviceos$Code_UNIQUE]
 GO



ALTER TABLE [dbo].[deviceos]
 ADD CONSTRAINT [deviceos$Code_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([Code] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'devicetypes$Code_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[devicetypes] DROP CONSTRAINT [devicetypes$Code_UNIQUE]
 GO



ALTER TABLE [dbo].[devicetypes]
 ADD CONSTRAINT [devicetypes$Code_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([Code] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'lookbookattributecategories$Code_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[lookbookattributecategories] DROP CONSTRAINT [lookbookattributecategories$Code_UNIQUE]
 GO



ALTER TABLE [dbo].[lookbookattributecategories]
 ADD CONSTRAINT [lookbookattributecategories$Code_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([Code] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'modules$idModules_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[modules] DROP CONSTRAINT [modules$idModules_UNIQUE]
 GO



ALTER TABLE [dbo].[modules]
 ADD CONSTRAINT [modules$idModules_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([idModule] ASC)

GO

IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'modules$Name_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[modules] DROP CONSTRAINT [modules$Name_UNIQUE]
 GO



ALTER TABLE [dbo].[modules]
 ADD CONSTRAINT [modules$Name_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([Name] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'permissions$idPermission_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[permissions] DROP CONSTRAINT [permissions$idPermission_UNIQUE]
 GO



ALTER TABLE [dbo].[permissions]
 ADD CONSTRAINT [permissions$idPermission_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([idPermission] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profiles$DeviceId_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[profiles] DROP CONSTRAINT [profiles$DeviceId_UNIQUE]
 GO



ALTER TABLE [dbo].[profiles]
 ADD CONSTRAINT [profiles$DeviceId_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([DeviceId] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'roles$idRole_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[roles] DROP CONSTRAINT [roles$idRole_UNIQUE]
 GO



ALTER TABLE [dbo].[roles]
 ADD CONSTRAINT [roles$idRole_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([idRole] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'socialupdates$ItemId_source_UQ'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[socialupdates] DROP CONSTRAINT [socialupdates$ItemId_source_UQ]
 GO



ALTER TABLE [dbo].[socialupdates]
 ADD CONSTRAINT [socialupdates$ItemId_source_UQ]
 UNIQUE 
   NONCLUSTERED ([IdSourceItem] ASC, [Source] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'sqlscriptlog$Id'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[sqlscriptlog] DROP CONSTRAINT [sqlscriptlog$Id]
 GO



ALTER TABLE [dbo].[sqlscriptlog]
 ADD CONSTRAINT [sqlscriptlog$Id]
 UNIQUE 
   NONCLUSTERED ([Id] ASC)

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'users$idUsers_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[users] DROP CONSTRAINT [users$idUsers_UNIQUE]
 GO



ALTER TABLE [dbo].[users]
 ADD CONSTRAINT [users$idUsers_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([IdUser] ASC)

GO

IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'users$Email_UNIQUE'  AND sc.name=N'dbo'  AND type in (N'UQ'))
ALTER TABLE [dbo].[users] DROP CONSTRAINT [users$Email_UNIQUE]
 GO



ALTER TABLE [dbo].[users]
 ADD CONSTRAINT [users$Email_UNIQUE]
 UNIQUE 
   NONCLUSTERED ([Email] ASC)

GO


USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'lookbookitemattributes'  AND sc.name = N'dbo'  AND si.name = N'attrib_item_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[lookbookitemattributes].[attrib_item_idx] 
GO
CREATE NONCLUSTERED INDEX [attrib_item_idx] ON [dbo].[lookbookitemattributes]
(
   [IdLookBookAttribute] ASC,
   [IdLookBookItem] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profiles'  AND sc.name = N'dbo'  AND si.name = N'fk_idstate_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profiles].[fk_idstate_idx] 
GO
CREATE NONCLUSTERED INDEX [fk_idstate_idx] ON [dbo].[profiles]
(
   [IdState] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'lookbookattributes'  AND sc.name = N'dbo'  AND si.name = N'fk_LookBookFilters_LookBookFiltersCategories1_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[lookbookattributes].[fk_LookBookFilters_LookBookFiltersCategories1_idx] 
GO
CREATE NONCLUSTERED INDEX [fk_LookBookFilters_LookBookFiltersCategories1_idx] ON [dbo].[lookbookattributes]
(
   [IdLookBookAttributeCategory] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profiles'  AND sc.name = N'dbo'  AND si.name = N'fk_Profiles_DeviceOs1_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profiles].[fk_Profiles_DeviceOs1_idx] 
GO
CREATE NONCLUSTERED INDEX [fk_Profiles_DeviceOs1_idx] ON [dbo].[profiles]
(
   [IdDeviceOs] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profiles'  AND sc.name = N'dbo'  AND si.name = N'fk_Profiles_DeviceTypes1_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profiles].[fk_Profiles_DeviceTypes1_idx] 
GO
CREATE NONCLUSTERED INDEX [fk_Profiles_DeviceTypes1_idx] ON [dbo].[profiles]
(
   [IdDeviceType] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'stores'  AND sc.name = N'dbo'  AND si.name = N'fk_Stores_States1_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[stores].[fk_Stores_States1_idx] 
GO
CREATE NONCLUSTERED INDEX [fk_Stores_States1_idx] ON [dbo].[stores]
(
   [IdState] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'lookbookitemattributes'  AND sc.name = N'dbo'  AND si.name = N'Item_attrib_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[lookbookitemattributes].[Item_attrib_idx] 
GO
CREATE NONCLUSTERED INDEX [Item_attrib_idx] ON [dbo].[lookbookitemattributes]
(
   [IdLookBookItem] ASC,
   [IdLookBookAttribute] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'campaign'  AND sc.name = N'dbo'  AND si.name = N'IX_Campaign_LockId' AND so.type in (N'U'))
   DROP INDEX [dbo].[campaign].[IX_Campaign_LockId] 
GO
CREATE NONCLUSTERED INDEX [IX_Campaign_LockId] ON [dbo].[campaign]
(
   [LockId] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'campaign'  AND sc.name = N'dbo'  AND si.name = N'IX_Campaign_Step' AND so.type in (N'U'))
   DROP INDEX [dbo].[campaign].[IX_Campaign_Step] 
GO
CREATE NONCLUSTERED INDEX [IX_Campaign_Step] ON [dbo].[campaign]
(
   [Step] ASC,
   [CampaignId] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'coupons'  AND sc.name = N'dbo'  AND si.name = N'ix_coupons_idprofile_group' AND so.type in (N'U'))
   DROP INDEX [dbo].[coupons].[ix_coupons_idprofile_group] 
GO
CREATE NONCLUSTERED INDEX [ix_coupons_idprofile_group] ON [dbo].[coupons]
(
   [IdProfileGroup] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'enabledsections'  AND sc.name = N'dbo'  AND si.name = N'ix_es_IdDeviceOs_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[enabledsections].[ix_es_IdDeviceOs_idx] 
GO
CREATE NONCLUSTERED INDEX [ix_es_IdDeviceOs_idx] ON [dbo].[enabledsections]
(
   [IdDeviceOs] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'stores'  AND sc.name = N'dbo'  AND si.name = N'ix_geolocationprocessing' AND so.type in (N'U'))
   DROP INDEX [dbo].[stores].[ix_geolocationprocessing] 
GO
CREATE NONCLUSTERED INDEX [ix_geolocationprocessing] ON [dbo].[stores]
(
   [GeolocationProcessingDatetime] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profilegroupdeviceos'  AND sc.name = N'dbo'  AND si.name = N'ix_IdDeviceOs_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profilegroupdeviceos].[ix_IdDeviceOs_idx] 
GO
CREATE NONCLUSTERED INDEX [ix_IdDeviceOs_idx] ON [dbo].[profilegroupdeviceos]
(
   [IdDeviceOs] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'imagesizes'  AND sc.name = N'dbo'  AND si.name = N'ix_IdDeviceOs_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[imagesizes].[ix_IdDeviceOs_idx] 
GO
CREATE NONCLUSTERED INDEX [ix_IdDeviceOs_idx] ON [dbo].[imagesizes]
(
   [IdDeviceOs] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profilegroupdeviceos'  AND sc.name = N'dbo'  AND si.name = N'ix_IdProfileGroup_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profilegroupdeviceos].[ix_IdProfileGroup_idx] 
GO
CREATE NONCLUSTERED INDEX [ix_IdProfileGroup_idx] ON [dbo].[profilegroupdeviceos]
(
   [IdProfileGroup] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'pendingnotification'  AND sc.name = N'dbo'  AND si.name = N'IX_PendingNotification_Campaign_Os_LockId' AND so.type in (N'U'))
   DROP INDEX [dbo].[pendingnotification].[IX_PendingNotification_Campaign_Os_LockId] 
GO
CREATE NONCLUSTERED INDEX [IX_PendingNotification_Campaign_Os_LockId] ON [dbo].[pendingnotification]
(
   [CampaignId] ASC,
   [Os] ASC,
   [LockId] ASC,
   [ProfileId] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'pendingnotification'  AND sc.name = N'dbo'  AND si.name = N'IX_PendingNotificationLockId' AND so.type in (N'U'))
   DROP INDEX [dbo].[pendingnotification].[IX_PendingNotificationLockId] 
GO
CREATE NONCLUSTERED INDEX [IX_PendingNotificationLockId] ON [dbo].[pendingnotification]
(
   [LockId] ASC,
   [CampaignId] ASC,
   [ProfileId] ASC,
   [Xid] ASC,
   [Os] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profilegroupmembers'  AND sc.name = N'dbo'  AND si.name = N'ix_pgm_group_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profilegroupmembers].[ix_pgm_group_idx] 
GO
CREATE NONCLUSTERED INDEX [ix_pgm_group_idx] ON [dbo].[profilegroupmembers]
(
   [IdProfileGroup] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profilegroupmembers'  AND sc.name = N'dbo'  AND si.name = N'ix_pgm_profile_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profilegroupmembers].[ix_pgm_profile_idx] 
GO
CREATE NONCLUSTERED INDEX [ix_pgm_profile_idx] ON [dbo].[profilegroupmembers]
(
   [IdProfile] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profilegroupstates'  AND sc.name = N'dbo'  AND si.name = N'ix_pgs_group_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profilegroupstates].[ix_pgs_group_idx] 
GO
CREATE NONCLUSTERED INDEX [ix_pgs_group_idx] ON [dbo].[profilegroupstates]
(
   [IdProfileGroup] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profilegroupstates'  AND sc.name = N'dbo'  AND si.name = N'ix_pgs_state_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profilegroupstates].[ix_pgs_state_idx] 
GO
CREATE NONCLUSTERED INDEX [ix_pgs_state_idx] ON [dbo].[profilegroupstates]
(
   [IdState] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF  EXISTS (
       SELECT * FROM sys.objects  so JOIN sys.indexes si
       ON so.object_id = si.object_id
       JOIN sys.schemas sc
       ON so.schema_id = sc.schema_id
       WHERE so.name = N'profilegroupzipcodes'  AND sc.name = N'dbo'  AND si.name = N'ix_pgz_group_idx' AND so.type in (N'U'))
   DROP INDEX [dbo].[profilegroupzipcodes].[ix_pgz_group_idx] 
GO
CREATE NONCLUSTERED INDEX [ix_pgz_group_idx] ON [dbo].[profilegroupzipcodes]
(
   [IdProfileGroup] ASC
)
WITH (SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF) ON [PRIMARY] 
GO
GO

USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'coupons$fk_ix_coupons_profilegroupid'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[coupons] DROP CONSTRAINT [coupons$fk_ix_coupons_profilegroupid]
 GO



ALTER TABLE [dbo].[coupons]
 ADD CONSTRAINT [coupons$fk_ix_coupons_profilegroupid]
 FOREIGN KEY 
   ([IdProfileGroup])
 REFERENCES 
   [Cuponera].[dbo].[profilegroups]     ([IdProfileGroup])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'enabledsections$ix_es_IdDeviceOs'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[enabledsections] DROP CONSTRAINT [enabledsections$ix_es_IdDeviceOs]
 GO



ALTER TABLE [dbo].[enabledsections]
 ADD CONSTRAINT [enabledsections$ix_es_IdDeviceOs]
 FOREIGN KEY 
   ([IdDeviceOs])
 REFERENCES 
   [Cuponera].[dbo].[deviceos]     ([IdDeviceOs])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'imagesizes$ix_IdDeviceOs'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[imagesizes] DROP CONSTRAINT [imagesizes$ix_IdDeviceOs]
 GO



ALTER TABLE [dbo].[imagesizes]
 ADD CONSTRAINT [imagesizes$ix_IdDeviceOs]
 FOREIGN KEY 
   ([IdDeviceOs])
 REFERENCES 
   [Cuponera].[dbo].[deviceos]     ([IdDeviceOs])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'lookbookattributes$fk_LookBookFilters_LookBookFiltersCategories1'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[lookbookattributes] DROP CONSTRAINT [lookbookattributes$fk_LookBookFilters_LookBookFiltersCategories1]
 GO



ALTER TABLE [dbo].[lookbookattributes]
 ADD CONSTRAINT [lookbookattributes$fk_LookBookFilters_LookBookFiltersCategories1]
 FOREIGN KEY 
   ([IdLookBookAttributeCategory])
 REFERENCES 
   [Cuponera].[dbo].[lookbookattributecategories]     ([IdLookBookAttributeCategory])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'lookbookitemattributes$fk_LookBookItemAttributes_LookBookAttributes1'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[lookbookitemattributes] DROP CONSTRAINT [lookbookitemattributes$fk_LookBookItemAttributes_LookBookAttributes1]
 GO



ALTER TABLE [dbo].[lookbookitemattributes]
 ADD CONSTRAINT [lookbookitemattributes$fk_LookBookItemAttributes_LookBookAttributes1]
 FOREIGN KEY 
   ([IdLookBookAttribute])
 REFERENCES 
   [Cuponera].[dbo].[lookbookattributes]     ([IdLookBookAttribute])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO

IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'lookbookitemattributes$fk_LookBookItemAttributes_LookBookItems1'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[lookbookitemattributes] DROP CONSTRAINT [lookbookitemattributes$fk_LookBookItemAttributes_LookBookItems1]
 GO



ALTER TABLE [dbo].[lookbookitemattributes]
 ADD CONSTRAINT [lookbookitemattributes$fk_LookBookItemAttributes_LookBookItems1]
 FOREIGN KEY 
   ([IdLookBookItem])
 REFERENCES 
   [Cuponera].[dbo].[lookbookitems]     ([IdLookBookItem])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupdeviceos$ix_pgd_IdDeviceOs'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profilegroupdeviceos] DROP CONSTRAINT [profilegroupdeviceos$ix_pgd_IdDeviceOs]
 GO



ALTER TABLE [dbo].[profilegroupdeviceos]
 ADD CONSTRAINT [profilegroupdeviceos$ix_pgd_IdDeviceOs]
 FOREIGN KEY 
   ([IdDeviceOs])
 REFERENCES 
   [Cuponera].[dbo].[deviceos]     ([IdDeviceOs])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO

IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupdeviceos$ix_pgd_IdProfileGroup'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profilegroupdeviceos] DROP CONSTRAINT [profilegroupdeviceos$ix_pgd_IdProfileGroup]
 GO



ALTER TABLE [dbo].[profilegroupdeviceos]
 ADD CONSTRAINT [profilegroupdeviceos$ix_pgd_IdProfileGroup]
 FOREIGN KEY 
   ([IdProfileGroup])
 REFERENCES 
   [Cuponera].[dbo].[profilegroups]     ([IdProfileGroup])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupmembers$ix_pgm_group'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profilegroupmembers] DROP CONSTRAINT [profilegroupmembers$ix_pgm_group]
 GO



ALTER TABLE [dbo].[profilegroupmembers]
 ADD CONSTRAINT [profilegroupmembers$ix_pgm_group]
 FOREIGN KEY 
   ([IdProfileGroup])
 REFERENCES 
   [Cuponera].[dbo].[profilegroups]     ([IdProfileGroup])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO

IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupmembers$ix_pgm_profile'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profilegroupmembers] DROP CONSTRAINT [profilegroupmembers$ix_pgm_profile]
 GO



ALTER TABLE [dbo].[profilegroupmembers]
 ADD CONSTRAINT [profilegroupmembers$ix_pgm_profile]
 FOREIGN KEY 
   ([IdProfile])
 REFERENCES 
   [Cuponera].[dbo].[profiles]     ([IdProfile])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupstates$ix_pgs_group'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profilegroupstates] DROP CONSTRAINT [profilegroupstates$ix_pgs_group]
 GO



ALTER TABLE [dbo].[profilegroupstates]
 ADD CONSTRAINT [profilegroupstates$ix_pgs_group]
 FOREIGN KEY 
   ([IdProfileGroup])
 REFERENCES 
   [Cuponera].[dbo].[profilegroups]     ([IdProfileGroup])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO

IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupstates$ix_pgs_state'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profilegroupstates] DROP CONSTRAINT [profilegroupstates$ix_pgs_state]
 GO



ALTER TABLE [dbo].[profilegroupstates]
 ADD CONSTRAINT [profilegroupstates$ix_pgs_state]
 FOREIGN KEY 
   ([IdState])
 REFERENCES 
   [Cuponera].[dbo].[states]     ([IdState])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profilegroupzipcodes$ix_pgz_group'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profilegroupzipcodes] DROP CONSTRAINT [profilegroupzipcodes$ix_pgz_group]
 GO



ALTER TABLE [dbo].[profilegroupzipcodes]
 ADD CONSTRAINT [profilegroupzipcodes$ix_pgz_group]
 FOREIGN KEY 
   ([IdProfileGroup])
 REFERENCES 
   [Cuponera].[dbo].[profilegroups]     ([IdProfileGroup])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profiles$fk_Profiles_DeviceOs1'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profiles] DROP CONSTRAINT [profiles$fk_Profiles_DeviceOs1]
 GO



ALTER TABLE [dbo].[profiles]
 ADD CONSTRAINT [profiles$fk_Profiles_DeviceOs1]
 FOREIGN KEY 
   ([IdDeviceOs])
 REFERENCES 
   [Cuponera].[dbo].[deviceos]     ([IdDeviceOs])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO

IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profiles$fk_Profiles_DeviceTypes1'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profiles] DROP CONSTRAINT [profiles$fk_Profiles_DeviceTypes1]
 GO



ALTER TABLE [dbo].[profiles]
 ADD CONSTRAINT [profiles$fk_Profiles_DeviceTypes1]
 FOREIGN KEY 
   ([IdDeviceType])
 REFERENCES 
   [Cuponera].[dbo].[devicetypes]     ([IdDeviceType])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO

IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'profiles$fk_idstate'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[profiles] DROP CONSTRAINT [profiles$fk_idstate]
 GO



ALTER TABLE [dbo].[profiles]
 ADD CONSTRAINT [profiles$fk_idstate]
 FOREIGN KEY 
   ([IdState])
 REFERENCES 
   [Cuponera].[dbo].[states]     ([IdState])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO


USE Cuponera
GO
IF EXISTS (SELECT * FROM sys.objects so JOIN sys.schemas sc ON so.schema_id = sc.schema_id WHERE so.name = N'stores$IdState'  AND sc.name=N'dbo'  AND type in (N'F'))
ALTER TABLE [dbo].[stores] DROP CONSTRAINT [stores$IdState]
 GO



ALTER TABLE [dbo].[stores]
 ADD CONSTRAINT [stores$IdState]
 FOREIGN KEY 
   ([IdState])
 REFERENCES 
   [Cuponera].[dbo].[states]     ([IdState])
    ON DELETE NO ACTION
    ON UPDATE NO ACTION

GO

